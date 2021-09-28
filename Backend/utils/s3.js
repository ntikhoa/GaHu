const S3 = require('aws-sdk/clients/s3');
const Constants = require('../utils/Constants');
const { v4: uuidv4 } = require('uuid');

const s3 = new S3({
    region: Constants.AWS_BUCKET_REGION,
    accessKeyId: Constants.AWS_ACCESS_KEY,
    secretAccessKey: Constants.AWS_SECRET_KEY
});


//uploads a file to s3
module.exports.uploadToS3 = (file) => {
    const fileExtension = file.originalname.split('.')[1];
    const fileName = uuidv4() + '.' + fileExtension;

    const uploadParams = {
        Bucket: Constants.AWS_BUCKET_NAME,
        Body: file.buffer,
        Key: fileName,
        ContentType: 'image/png image/jpeg image/jpg'
    }

    return s3.upload(uploadParams).promise();
}

//downloads a file from s3
// exports.downloadsFromS3 = (fileKey) => {
//     const downloadParams = {
//         Key: fileKey,
//         Bucket: bucketName
//     }

//     return s3.getObject(downloadParams).createReadStream();
// }

//delete a file from s3
module.exports.deleteFromS3 = (key) => {
    const deleteParams = {
        Bucket: Constants.AWS_BUCKET_NAME,
        Key: key,
    };

    return s3.deleteObject(deleteParams).promise();
}