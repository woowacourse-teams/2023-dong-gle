FROM nginx

RUN rm /etc/nginx/conf.d/default.conf
COPY ./default.conf /etc/nginx/conf.d/default.conf 
COPY ./dist /usr/share/nginx/html

EXPOSE 3000