FROM nginx:1.23.1

# Set working directory
WORKDIR /app

# Copy over static assets
COPY public public/

# Copy over entrypoint
COPY docker/entrypoints/nginx-entrypoint.sh /usr/local/bin/nginx-entrypoint.sh

# Copy Nginx config template
RUN rm /etc/nginx/conf.d/default.conf
COPY infractructure/nginx/conf.d/default.conf /etc/nginx/conf.d/default.conf

# Nginx init
RUN ["chmod", "+x", "/usr/local/bin/nginx-entrypoint.sh"]
ENTRYPOINT ["/usr/local/bin/nginx-entrypoint.sh"]
