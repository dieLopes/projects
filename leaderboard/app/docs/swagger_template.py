swagger_template = {
    "swagger": "2.0",
    "info": {
        "title": "CrossFit API",
        "version": "1.0",
        "description": "API for managing CrossFit athletes, workouts and scores"
    },
    "securityDefinitions": {
        "Bearer": {
            "type": "apiKey",
            "name": "Authorization",
            "in": "header",
            "description": "JWT Authorization header using the Bearer scheme. Example: 'Bearer {token}'"
        }
    },
    "security": [{"Bearer": []}]
}
