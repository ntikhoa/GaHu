const express = require('express');
const controller = require('../controllers/auth');
const { isPasswordConfirmMatched, validateRegister, checkExistedUserByEmail } = require('../midllewares/auth');
const catchAsync = require('../utils/catchAsync');

const router = express.Router();


router.post('/register', validateRegister,
    catchAsync(checkExistedUserByEmail),
    isPasswordConfirmMatched,
    catchAsync(controller.register));

module.exports = router;