import RPi.GPIO as GPIO
import time

#핀세팅
ENA = 14
IN1 = 15
IN2 = 18
IN3 = 16
IN4 = 20
ENB = 21

GPIO.setmode(GPIO.BCM)  #핀 모드 설정

GPIO.setup(ENA, GPIO.OUT)
GPIO.setup(IN1, GPIO.OUT)
GPIO.setup(IN2, GPIO.OUT)
GPIO.setup(IN3, GPIO.OUT)
GPIO.setup(IN4, GPIO.OUT)
GPIO.setup(ENB, GPIO.OUT)

pwmA = GPIO.PWM(ENA, 100)
pwmA.start(0)   #제어시작

pwmB = GPIO.PWM(ENB, 100)
pwmB.start(0)   #제어시작

def forward():  #전진시 핀 설정
    GPIO.output(IN1, GPIO.HIGH)
    GPIO.output(IN2, GPIO.LOW)   
    GPIO.output(IN3, GPIO.HIGH)
    GPIO.output(IN4, GPIO.LOW)

def stop():     #정지시 핀 설정 및 pwm 제어
    print("stop")
    GPIO.output(IN1, GPIO.LOW)
    GPIO.output(IN2, GPIO.LOW)   
    GPIO.output(IN3, GPIO.LOW)
    GPIO.output(IN4, GPIO.LOW)
    pwmA.ChangeDutyCycle(0)
    pwmB.ChangeDutyCycle(0)

def ctrl(stat, steer):  #stat : 0이면 정지, 1이면 전진  steer : 조향값
    if (stat == 1):
        forward()
    else:
        stop()
        return

    std_spd=50  #기본 속력 설정

    if steer < 0: #steer<0 일때 왼쪽 바퀴가 더 느리게 돌아야함
        pwma = std_spd + (steer)
        if pwma < 0:
            pwma = 0
        pwmb = std_spd
    else:       #steer>0 일때 오른쪽 바퀴가 더 느리게 돌아야함(0일때는 상관없음)
        pwma = std_spd
        pwmb = std_spd - (steer)  #steer*2를 해줌으로써 pwmb값의 범위: 0~100
        if pwmb < 0:
            pwmb = 0

    pwmA.ChangeDutyCycle(pwma)  #제어
    pwmB.ChangeDutyCycle(pwmb)  #제어