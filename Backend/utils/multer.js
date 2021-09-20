const multer = require('multer');
const { v4: uuidv4 } = require('uuid');
const Constants = require('./Constants');

const fileStorage = multer.diskStorage({
    destination: (req, res, cb) => {
        cb(null, 'images');
    },
    filename: (req, file, cb) => {
        const fileExtension = file.originalname.split('.')[1];
        cb(null, uuidv4() + '.' + fileExtension);
    }
})

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