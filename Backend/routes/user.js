const express = require('express');
const { isAuth } = require('../midllewares/auth');
const catchAsync = require('../utils/catchAsync');
const controller = require('../controllers/user');

const router = express.Router();

router.get('/',
    catchAsync(isAuth),
    controller.getUserInfo);

module.exports = router;