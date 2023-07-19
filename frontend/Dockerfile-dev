FROM node:18-alpine as builder
WORKDIR /app
COPY package.json .
RUN yarn install
COPY ./ ./
RUN yarn build

FROM nginx

RUN rm /etc/nginx/conf.d/default.conf
COPY ./default.conf /etc/nginx/conf.d/default.conf 
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 3000