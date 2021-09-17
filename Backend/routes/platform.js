const express = require('express');
const controller = require('../controllers/platform');
const { isAuth } = require('../midllewares/auth');
const catchAsync = require('../utils/catchAsync');

const router = express.Router();

router.get('/',
    catchAsync(isAuth),
    catchAsync(controller.getAllPlatform));

module.exports = router;