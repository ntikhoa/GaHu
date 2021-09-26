module.exports = {
    BASE_URL: 'https://gahu.herokuapp.com',
    MONGO_DB_NAME: process.env.MONGO_DB_NAME,
    SECRET_SIGNATURE: process.env.SECRET_SIGNATURE,
    SALT: 12,
    BAD_REQUEST: "Bad Request",
    NOT_FOUND: "Not Found",
    UNAUTHORIZED: "Unauthorized",
    FORBIDDEN: "Forbidden",
    UNSUPPORTED_MEDIA_TYPE: "Unsupported Media Type",
    FILE_MAX_SIZE: 1_048_576,  //bytes => 1MB
    MULTER_ERROR_CODE_LIMIT_SIZE: "LIMIT_FILE_SIZE",
    IMAGE_RATIO: 1.5,
    ADMIN_EMAILS: ['ntikhoa123@gmail.com', 'ntikhoa@gmail.com'],
    PER_PAGE: 10
}