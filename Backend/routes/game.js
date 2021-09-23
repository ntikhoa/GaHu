const express = require('express');
const catchAsync = require('../utils/catchAsync');
const controller = require('../controllers/game');
const { isAuth, isAdmin } = require('../midllewares/auth');
const { validateCreateGameBody, validateGetGameDetail, isGameAuthor, validateUpdateGameBody, validatePlatformIds } = require('../midllewares/game');
const { uploadImage } = require('../midllewares/uploadImage');

const router = express.Router();

router.post('/',
    catchAsync(isAuth),
    isAdmin,
    uploadImage,
    validateCreateGameBody,
    catchAsync(validatePlatformIds),
    catchAsync(controller.createGame));

router.get('/:id',
    catchAsync(isAuth),
    catchAsync(validateGetGameDetail),
    controller.getGameDetail);

router.delete('/:id',
    catchAsync(isAuth),
    catchAsync(validateGetGameDetail),
    isGameAuthor,
    catchAsync(controller.deleteGame))

router.patch('/:id',
    catchAsync(isAuth),
    catchAsync(validateGetGameDetail),
    isGameAuthor,
    uploadImage,
    validateUpdateGameBody,
    catchAsync(validatePlatformIds),
    catchAsync(controller.updateGame)
);

module.exports = router;