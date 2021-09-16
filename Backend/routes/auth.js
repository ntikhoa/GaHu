const express = require('express');
const controller = require('../controllers/auth');
const catchAsync = require('../utils/catchAsync');
const { isPasswordConfirmMatched, validateRegister, checkRegisteredUserByEmail, isEmailExist, isPasswordCorrect, isAuth, isNewPasswordConfirmMatched, validateNewPassword } = require('../midllewares/auth');

const router = express.Router();


router.post('/register',
    validateRegister,
    isPasswordConfirmMatched,
    catchAsync(checkRegisteredUserByEmail),
    catchAsync(controller.register));

router.post('/login',
    catchAsync(isEmailExist),
    catchAsync(isPasswordCorrect),
    controller.login);

router.post('/changePassword',
    catchAsync(isAuth),
    validateNewPassword,
    isNewPasswordConfirmMatched,
    catchAsync(isPasswordCorrect),
    catchAsync(controller.changePassword)
)

module.exports = router;