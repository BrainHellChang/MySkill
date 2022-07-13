import cv2
import numpy as np
import time
import car

#CONST
ROI_Y = 290 #roi영역 y축 중간값 설정
STOPSIGN_Y = 240 #정지선 검출할 y축 값 설정
STOP = 0
GO = 1

cap = cv2.VideoCapture(-1)

def region_of_interest(img, vertices, color3=(255,255,255), color1=255): # ROI 셋팅

    mask = np.zeros_like(img) # mask = img와 같은 크기의 빈 이미지
    
    if len(img.shape) > 2: # Color 이미지(3채널)일때
        color = color3
    else: # 흑백 이미지(1채널)일때
        color = color1
        
    # vertices에 정한 점들로 이뤄진 다각형부분(ROI 설정부분)을 color로 채움 
    cv2.fillPoly(mask, vertices, color)
    
    # 이미지와 color로 채워진 ROI를 합침
    ROI_image = cv2.bitwise_and(img, mask)
    return ROI_image, mask

def get_equation(x1, y1, x2, y2):   #기울기, 절편값 구하는 함수
    if x1!=x2:
        m = (y2-y1)/(x2-x1)
        n = (y1 - (m*x1))
    else:
        m = 0
        n = 0
    return m, n

while(cap.isOpened()):
    ret, frame = cap.read()
    if(ret):
        frame = cv2.flip(frame, 0)
        frame = cv2.flip(frame, 1)

        src = frame
        copy = src.copy()
        height, width = src.shape[:2]
        vertices = np.array([[(0,height), (0, height/5*3), (40, height/5*2), (width-40, height/5*2), (width, height/5*3), (width,height)]], dtype=np.int32)

        gray_img = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)    #그레이스케일변환
        blur_img = cv2.GaussianBlur(gray_img, (5, 5), 0)    #이미지 블러 처리
        roi_img, mask = region_of_interest(blur_img, vertices)  #roi 설정
        ret, binary = cv2.threshold(roi_img, 160, 255, cv2.THRESH_BINARY)   #이진화
        canny_img = cv2.Canny(binary, 70, 210)  #엣지 검출
        lines = cv2.HoughLinesP(canny_img, 1, 1* np.pi/180, 15, np.array([]), minLineLength=15, maxLineGap=100) #직선 검출

        stopsign =[]    #정지선 검출 코드
        for c in range(200, 451, 50):   #정지선 검출 범위 설정 range(x축 시작, x축 끝, 간격)
            stopsign.append(binary[STOPSIGN_Y, c])# stopsign 배열에 픽셀색상(0 or 255) append
            cv2.circle(copy, (c, STOPSIGN_Y), 3, [100,100,0], -1)
        print(stopsign)
        if sum(stopsign) == 255*len(stopsign):  #stopsign배열에 모든 원소가 255일때 ==>정지선으로 인식
            car.ctrl(STOP, 0)  #차량정지
            print('stop')
            time.sleep(3)
            continue

        x_left_list , x_right_list = [], []     # roi영역 내 x값 검출 리스트
        x_left_avg, x_right_avg = 0-100, width+100  #왼쪽 오른쪽 avg 초기화

        if lines is not None:
                for line in lines:
                    for x1, y1, x2, y2 in line:
                        
                        m, n = get_equation(x1, y1, x2, y2)
                        if -0.2<m<0.2: #기울기가 수평 근처인 선 제거
                            continue

                        cv2.line(copy, (x1, y1), (x2, y2), [0,0,255], 2) #houghline
                        cv2.circle(copy, (x1, y1), 5, [0,255,0], -1)    #hough point1
                        cv2.circle(copy, (x2, y2), 5, [0,255,0], -1)    #hough point2

                        roi_x = (ROI_Y-n)/m
                        if x2<x1:
                            if x2>roi_x or roi_x>x1:
                                continue
                        else:
                            if x1>roi_x or roi_x>x2:
                                continue
                        cv2.circle(copy, (int(roi_x), ROI_Y), 5, [100,100,0], -1)   #transaction between line&ROI_Y
                        if roi_x < width/2:
                            x_left_list.append(roi_x)
                        else:
                            x_right_list.append(roi_x)

        if len(x_left_list)!=0:
            x_left_avg = sum(x_left_list)/len(x_left_list) #roi_x의 평균값
        if len(x_right_list)!=0:
            x_right_avg = sum(x_right_list)/len(x_right_list) #roi_x의 평균값

        x_avg = (x_left_avg + x_right_avg)/2    #차량 회전 결정
        from_center = (width/2 - x_avg) *-1     #중앙으로부터의 거리
        steer = from_center/2   #조향값 조절

        cv2.line(copy, (int(x_left_avg),ROI_Y-15),(int(x_left_avg),ROI_Y+15), [255,255,100],3)    #left_avg line
        cv2.line(copy, (int(x_right_avg),ROI_Y-15),(int(x_right_avg),ROI_Y+15), [255,255,100],3) #right_avg line
        cv2.line(copy, (int(x_avg),ROI_Y-15),(int(x_avg),ROI_Y+15), [255,255,0],3)   #x_avg line
        cv2.line(copy, (int(width/2), ROI_Y-10), (int(width/2), ROI_Y+10), [255,0,0], 3)   #center line
        cv2.rectangle(copy, (0, ROI_Y+20), (width, ROI_Y-20), [0,255,0], 2)  #roi rectangle
        cv2.line(copy, (0, ROI_Y), (width, ROI_Y), [255,0,0], 1)   #roi_y center line

        cv2.imshow('lines', copy)
        #cv2.imshow('binary', binary)

        car.ctrl(GO, steer = steer)  #차량제어

        print('from_center: {}, steer: {}'.format(from_center, steer))

        k = cv2.waitKey(1)  #esc키 누를시 종료
        if(k==27):
            break

# print(x_left_list)
# print(x_left_avg)
# print(x_right_list)
# print(x_right_avg)
# print(x_left_avg)
# print(x_right_avg)
# print(x_avg)
#print('steer: {}'.format(steer))

#print(len(lines))   #검출 라인 갯수 출력
#print(lines)        #검출 라인 출력
#print(src.shape)    #이미지 크기 출력

#cv2.imshow('gray_img', gray_img)
#cv2.imshow('blur_img', blur_img)
#cv2.imshow('roi_img', roi_img)
#cv2.imshow('mask', mask)
#cv2.imshow('binary', binary)
#cv2.imshow('canny_img', canny_img)

#cv2.imshow('lines', copy)
#cv2.waitKey(0)

cap.release()
cv2.destroyAllWindows()