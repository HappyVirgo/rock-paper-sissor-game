FROM nginx:1.23.1

# Set working directory
WORKDIR /app

# Copy Nginx config template
RUN rm /etc/nginx/conf.d/default.conf
COPY ./infrastructure/nginx/conf.d/default.conf /etc/nginx/conf.d/default.conf

# Nginx init
ENTRYPOINT ["nginx", "-g", "daemon off;"]
