#!/bin/bash

# deploy.sh가 실행 중인지 확인
if [ -e /tmp/deploy.lock ]; then
    echo "Deployment is in progress"
    exit 1
fi

# deploy.sh가 실행 중이지 않다면 lock 파일 생성
touch /tmp/deploy.lock

# Blue 를 기준으로 현재 떠있는 컨테이너를 체크한다.
EXIST_BLUE=$(sudo docker compose -p compose-blue -f compose-blue.yml ps | grep Up)
 
# 컨테이너 스위칭
if [ -z "$EXIST_BLUE" ]; then
    echo "blue up"
    sudo docker compose -p compose-blue -f compose-blue.yml pull
    sudo docker compose -p compose-blue -f compose-blue.yml up -d
    BEFORE_COMPOSE_COLOR="green"
    AFTER_COMPOSE_COLOR="blue"
    export BACKEND_PORT=8080
    export FRONTEND_PORT=3000
else
    echo "green up"
    sudo docker compose -p compose-green -f compose-green.yml pull
    sudo docker compose -p compose-green -f compose-green.yml up -d
    BEFORE_COMPOSE_COLOR="blue"
    AFTER_COMPOSE_COLOR="green"
    export BACKEND_PORT=8081
    export FRONTEND_PORT=3001
fi
 
sleep 10
INFRA_PROFILE=$1
 
# 새로운 컨테이너가 제대로 떴는지 확인
EXIST_AFTER=$(sudo docker compose -p compose-${AFTER_COMPOSE_COLOR} -f compose-${AFTER_COMPOSE_COLOR}.yml ps | grep Up)
if [ -n "$EXIST_AFTER" ]; then
  # nginx.config를 컨테이너에 맞게 변경해주고 reload 한다
  envsubst < conf-${INFRA_PROFILE}/nginx.template > conf-${INFRA_PROFILE}/nginx.conf
  sudo docker compose -f compose-nginx.yml exec nginx nginx -s reload
 
  # 이전 컨테이너 종료
  sudo docker compose -p compose-${BEFORE_COMPOSE_COLOR} -f compose-${BEFORE_COMPOSE_COLOR}.yml down
  echo "$BEFORE_COMPOSE_COLOR down"

  # lock 파일 삭제
  rm -f /tmp/deploy.lock
else 
  echo "deploy fail"
  rm -f /tmp/deploy.lock
  exit 1
fi