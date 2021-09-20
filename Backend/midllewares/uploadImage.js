const upload = require('../utils/multer');
const ExpressError = require('../utils/ExpressError');
const Constants = require('../utils/Constants');
const imageSize = require('image-size');

module.exports.uploadImage = (req, res, next) => {
    upload(req, res, function (err) {
        if (err) {
            if (err.code && err.code === Constants.MULTER_ERROR_CODE_LIMIT_SIZE) {
                next(new ExpressError('Image size is over 1MB', Constants.BAD_REQUEST, 400));
            } else {
                next(new ExpressError('Invalid image type', Constants.UNSUPPORTED_MEDIA_TYPE, 415));
            }
        } else {
            const imageDimension = imageSize(req.file.path.replace("\\", "/"));
            const ratio = imageDimension.width / imageDimension.height;
            if (ratio < (Constants.IMAGE_RATIO - 0.2)
                || ratio > (Constants.IMAGE_RATIO + 0.2)) {
                next(new ExpressError('Invalid image ratio', Constants.BAD_REQUEST, 400));
            }
            next();
        }
    });
}