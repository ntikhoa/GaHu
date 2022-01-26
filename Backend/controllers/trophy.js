module.exports.crawlTrophy = (req, res) => {
    const $ = req.$

    const profile = getTotalProfile($)
    const games = getAllTrophies($)
    res.status(200).json({
        data: {
            profile: profile,
            recent_played: games
        },
        message: "Get trophies successfully"
    })
}

function getTotalProfile($) {
    const profileBar = $('.profile-bar')
    const stat = $('.stats .stat')

    mainProfile = {
        trophyLevel: profileBar.find('.trophy-count li').text(),
        totalTrophies: profileBar.find('.total').text(),
        platinum: profileBar.find('.platinum').text(),
        gold: profileBar.find('.gold').text(),
        silver: profileBar.find('.silver').text(),
        bronze: profileBar.find('.bronze').text(),
        gamesPlayed: $(stat[0]).text(),
        completion: $(stat[2]).text(),
        worldRank: $('.stats .rank a').text()
    }

    for (const property in mainProfile) {
        if (property == 'completion') {
            mainProfile[property] = parseFloat(mainProfile[property].replace(/[^\d.-]/g, ''))
        } else {
            mainProfile[property] = parseInt(mainProfile[property].replace(/[^\d.-]/g, ''))
        }
    }

    return mainProfile
}

function getAllTrophies($) {
    const games = $('#gamesTable tbody').find('tr')

    const gamesEntity = []

    for (let i = 0; i < 5 && i < games.length; i++) {

        const game = games[i]
        if ($(game).attr().id == 'load-more')
            return

        const number = $(game).find('.small-info b')
        const trophyCount = $(game).find('.trophy-count span')

        const gameEntity = {
            title: $(game).find('.title').text(),
            image: $(game).find('.game img').attr('src'),
            isPlat: $(game).attr().class == 'platinum',
            got: $(number[0]).text(),
            total: $(number[1]).text(),
            gold: $(trophyCount[1]).text(),
            silver: $(trophyCount[3]).text(),
            bronze: $(trophyCount[5]).text()
        }

        for (const property in gameEntity) {
            if (['got', 'total', 'gold', 'silver', 'bronze'].includes(property)) {
                gameEntity[property] = parseInt(gameEntity[property].replace(/[^\d.-]/g, ''))
            }
        }
        gamesEntity.push(gameEntity)
    }
    return gamesEntity
}