const express = require('express')
const catchAsync = require('../utils/catchAsync')
const { isAuth } = require('../midllewares/auth')
const controller = require('../controllers/trophy')
const { fetchHtml } = require('../midllewares/trophy')

const router = express.Router()

router.get('/',
    catchAsync(isAuth),
    catchAsync(fetchHtml),
    controller.crawlTrophy)

module.exports = router