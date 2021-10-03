const upload = require('../utils/multer');
const ExpressError = require('../utils/ExpressError');
const Constants = require('../utils/Constants');

module.exports.uploadImage = (req, res, next) => {
    upload(req, res, function (err) {
        if (err) {
            if (err.code && err.code === Constants.MULTER_ERROR_CODE_LIMIT_SIZE) {
                next(new ExpressError('Image size is over 1MB', 400));
            } else {
                next(new ExpressError('Invalid image type', 415));
            }
        }
        next();
    });
}