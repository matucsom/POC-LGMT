FROM node:18-alpine

WORKDIR /app

RUN apk add --no-cache libc6-compat

COPY package*.json ./ 

RUN npm install

COPY . .

RUN npm run build

EXPOSE 5173

CMD ["npm", "run", "dev", "--", "--host"]