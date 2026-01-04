# ================================
# BUILD STAGE
# ================================
FROM node:18-alpine AS build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci --legacy-peer-deps

# Copy source code
COPY . .

# Build the application
RUN npm run build

# ================================
# PRODUCTION STAGE
# ================================
FROM nginx:alpine

# Copy custom nginx configuration that enables SPA routing
# SPA routing: Single Page Application routing handled by client-side JavaScript
# Fixes 404 errors on page reload by serving index.html for client-side routes
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy built app to nginx
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
