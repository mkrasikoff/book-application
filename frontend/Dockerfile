# Stage 1: Build the Angular application
FROM node:16 as build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npx ng build --configuration production

# Stage 2: Serve the Angular application with Nginx
FROM nginx:alpine
COPY --from=build /app/dist/frontend /usr/share/nginx/html
