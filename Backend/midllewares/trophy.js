const Constants = require('../utils/Constants')
const axios = require('axios')
const cheerio = require('cheerio')
const ExpressError = require('../utils/ExpressError')

module.exports.fetchHtml = async (req, res, next) => {
    const user = req.user
    const emailName = user.email.split('@')[0]

    const url = Constants.BASE_TROPHY_CRAWL_URL + emailName
    const response = await axios.get(url)
    const $ = cheerio.load(response.data)

    if (!($('.stats').text() && $('.profile-bar').text())) {
        console.log('error')
        throw new ExpressError(Constants.NOT_FOUND, 400)
    }

    req.$ = $
    next()
}