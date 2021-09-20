const express = require('express');
const catchAsync = require('../utils/catchAsync');
const controller = require('../controllers/game');
const { isAuth, isAdmin } = require('../midllewares/auth');
const { validateCreateGameBody } = require('../midllewares/game');
const { uploadImage } = require('../midllewares/uploadImage');

const router = express.Router();

router.post('/',
    catchAsync(isAuth),
    isAdmin,
    uploadImage,
    validateCreateGameBody,
    catchAsync(controller.createGame));

module.exports = router;