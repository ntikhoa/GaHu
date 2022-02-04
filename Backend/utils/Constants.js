require('dotenv').config();

module.exports = {
    IMAGE_BASE_URL: 'https://gahu.s3.ap-southeast-1.amazonaws.com/',
    AWS_BUCKET_NAME: process.env.AWS_BUCKET_NAME,
    AWS_BUCKET_REGION: process.env.AWS_BUCKET_REGION,
    AWS_ACCESS_KEY: process.env.AWS_ACCESS_KEY,
    AWS_SECRET_KEY: process.env.AWS_SECRET_KEY,
    MONGO_USERNAME: process.env.MONGO_USERNAME,
    MONGO_PASSWORD: process.env.MONGO_PASSWORD,
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
    IMAGE_RATIO: 2,
    ADMIN_EMAILS: ['ntikhoa123@gmail.com', 'ntikhoa@gmail.com'],
    PER_PAGE: 10,
    BASE_TROPHY_CRAWL_URL: 'https://psnprofiles.com/'
}