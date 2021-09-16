const express = require('express');
const catchAsync = require('../utils/catchAsync');
const controller = require('../controllers/user');
const { isAuth } = require('../midllewares/auth');
const { validateUpdateUser, checkDuplicateUpdatedEmail } = require('../midllewares/user');

const router = express.Router();

router.get('/',
    catchAsync(isAuth),
    controller.getUser);

router.patch('/',
    catchAsync(isAuth),
    validateUpdateUser,
    catchAsync(checkDuplicateUpdatedEmail),
    catchAsync(controller.updateUser));

module.exports = router;