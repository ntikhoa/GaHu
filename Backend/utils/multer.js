const multer = require('multer');
const Constants = require('./Constants');

const fileStorage = multer.memoryStorage({});

const fileFilter = (req, file, cb) => {
    const mimetype = file.mimetype;
    if (mimetype === 'image/png'
        || mimetype === 'image/jpg'
        || mimetype === 'image/jpeg') {
        cb(null, true);
    } else {
        cb(null, false);
        return cb(new Error("Invalid image file"));
    }
}

module.exports = multer({
    storage: fileStorage,
    fileFilter: fileFilter,
    limits: {
        fileSize: Constants.FILE_MAX_SIZE
    }
}).single('image');