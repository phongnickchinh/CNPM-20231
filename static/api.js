
const ERROR = {
    USERNAME_DOESNOT_EXIST: 'USERNAME_DOESNOT_EXIST',
    WRONG_PASSWORD: 'WRONG_PASSWORD',
    EXISTED_USERNAME: 'EXISTED_USERNAME',
    ROOM_ID_DOESNOT_EXIST: 'ROOM_ID_DOESNOT_EXIST',
    USER_HAS_BEEN_IN_ROOM: 'USER_HAS_BEEN_IN_ROOM',
    ROOM_ADMIN_CAN_NOT_LEAVE_ROOM: 'ROOM_ADMIN_CAN_NOT_LEAVE_ROOM',
    NO_USER_LOGINED: 'No user is logged in',
    USER_DOESNOT_HAVE_SECURITY_QUESTION: 'USER_DOESNOT_HAVE_SECURITY_QUESTION',
    WRONG_ANSWER_SECURITY_QUESTION: 'WRONG_ANSWER_SECURITY_QUESTION',
    ERROR: 'Có lỗi xảy ra!'
}

const DEV_MODE = true
let api

function RandomFunction(seed = 1) {
    return (s = 0, e = 1) => {
        let x = Math.sin(seed++) * 10000;
        return s + (x - Math.floor(x)) * (e - s);
    }
}

function FakeAPI(helpTrueAPI = false) {
    let NUM_USERS = 50
    let MAX_NUM_SECURITY_QUESTIONS_PER_USER = 4
    let MAX_NUM_MEMBERS_PER_ROOM = 20
    let NUM_ROOMS = 80
    let MAX_NUM_JOIN_ROOM_REQUESTS_PER_USER = 2
    let MAX_NUM_SMALL_TRANSCATION_PER_USER_PER_ROOM = 50
    let MAX_NUM_FEE_WITH_DEADLINE = 100
    let API_DELAY = 100

    let random = RandomFunction()
    let randInt = (a, b) => Math.floor(random(a, b + 1))
    let sample = (arr) => arr[Math.floor(random()*arr.length)]
    let sample_n = (arr, n) => {
        let indices = new Set()
        for (let i = 0; i < n; i++) {
            let index = randInt(0, arr.length - 1)
            while (indices.has(index)) {
                index = randInt(0, arr.length - 1)
            }
            indices.add(index)
        }
        let result = []
        for (let index of indices) result.push(arr[index])
        return result
    }
    let randomDate = (after = null) => {
        if (!after) {
            let r = `${randInt(1, 28)}/${randInt(1, 12)}/${randInt(2020, 2023)}`
            while (CustomDateManager.d1SmallerThanD2('12/4/2023', r)) {
                r = `${randInt(1, 28)}/${randInt(1, 12)}/${randInt(2020, 2023)}`
            }
            return r
        }
        let [d, m, y] = after.split('/').map(i => Number(i))
        let n_y = randInt(y, 2023)
        if (n_y > y) {
            return `${randInt(1, 28)}/${randInt(1, 12)}/${n_y}`
        }
        let n_m = randInt(m, 12)
        if (n_m > m) {
            return `${randInt(1, 28)}/${n_m}/${n_y}`
        }
        let n_d = randInt(d, 28)
        if (n_d > d) {
            return `${n_d}/${n_m}/${n_y}`
        }
        return `${randInt(1, 28)}/${randInt(1, 12)}/${n_y + 1}`
    }
    let existedId = new Set()
    let randomId = (prefix = '') => {
        // return prefix + randInt(10E16, 10E17 - 1)
        let id = randInt(1, 2E9)
        while (existedId.has(id)) id = randInt(1, 2E9)
        existedId.add(id)
        return id
    }
    
    function initUsers(NUM_USERS) {
        let users = []
        let n1 = ['Nguyễn', 'Lê', 'Trần', 'Phạm', 'Hồ', 'Phan', 'Vũ', 'Dương']
        let n2 = ['Hoàng', 'Tiến', 'Minh', 'Văn', 'Thị', 'Quang', 'Duy', 'Anh', 'Hữu']
        let n3 = ['Hùng', 'Lực', 'Mạnh', 'Phong', 'Hiệp']
        let bankNames = ['Vietinbank', 'Agribank', 'Techcombank', 'Momo', 'Zalo pay', 'MB bank']
        for (let i = 0; i < NUM_USERS; i++) {
            let id = randomId()
            let fullname = [sample(n1), sample(n2), sample(n3)].join(' ')
            let username = '' + (i + 1)
            let password = username
            let phoneNumber = '0' + randInt(1E7, 1E8 - 1)
            let bankNumber = '' + randInt(1E9, 1E11 - 1)
            let bankName = sample(bankNames)
            let imageIndex = []
            for (let j = 0; j < 4; j++) imageIndex.push('default-avatar')
            for (let j = 0; j < 22; j++) imageIndex.push(j)
            let avatarUrl = './resources/user-images/' + sample(imageIndex, random) + '.jpg'
    
            users.push({
                id, fullname, username, password, phoneNumber, bankName, bankNumber, avatarUrl
            })
        }
        return users
    }
    function initSecurityQues(users, MAX_NUM_SECURITY_QUESTIONS_PER_USER) {
        let secQuess = []
        for (let user of users) {
            let userId = user.id
            let numQues = randInt(0, MAX_NUM_SECURITY_QUESTIONS_PER_USER)
            for (let j = 0; j < numQues; j++) {
                let id = randomId()
                let a = randInt(1E2, 1E3)
                let b = randInt(1E2, 1E3)
                secQuess.push({
                    userId, id,
                    question: `${a} + ${b} = ?`,
                    answer: '' + (a + b)
                })
            }
        }
        return secQuess
    }
    function initRooms(users, NUM_ROOMS, MAX_NUM_MEMBERS_PER_ROOM) {
        let rooms = []
        let room_user = []
        let roomNames1 = ['Trà đá', 'Anh em', 'Gia đình', 'Nhà trọ', 'Quán ăn', 'Anh em cây khế', 'Hội chị em', 'Hội anh em', 'Nhà ăn', 'Khu doanh trại']
        let roomNames2 = ['Bách Khoa', 'NEU', 'HUCE', 'KTX', 'B1', 'B3', 'IT1-01', 'Hà Nội', 'Thanh Hóa', 'Nghệ An', 'Hà Tĩnh']
        let addresses = ['Bách Khoa', 'NEU', 'Hà Nội', 'TP HCM']
        for (let i = 0; i < NUM_ROOMS; i++) {
            let id = randomId()
            let numUsers = randInt(1, MAX_NUM_MEMBERS_PER_ROOM)
            let usersInRoom = sample_n(users, numUsers)
            let adminUserId = sample(usersInRoom).id

            for (let user of usersInRoom) {
                let status = (adminUserId == user.id) ? 1 : randInt(0, 1)
                status = 1
                let joinDate = randomDate()
                let leaveDate = null
                if (status == 0) {
                    leaveDate = randomDate(joinDate)
                }
                room_user.push({
                    userId: user.id,
                    roomId: id,
                    status,
                    joinDate, leaveDate
                })
            }
            let roomName = [sample(roomNames1), sample(roomNames2), '-', randInt(1, 9)].join(' ')
            let address = sample(addresses)

            rooms.push({
                id, adminUserId, roomName, address
            })
        }
        return [rooms, room_user]
    }
    function initJoinReq(users, rooms, room_user, MAX_NUM_JOIN_ROOM_REQUESTS_PER_USER) {
        let joinRoomRequests = []
        for (let user of users) {
            let numRequests = randInt(0, Math.min(MAX_NUM_JOIN_ROOM_REQUESTS_PER_USER*10, rooms.length))
            let roomIdsHasThisUser = new Set(
                room_user
                .filter(({userId}) => userId == user.id)
                .map(({userId}) => userId)
            )
            let allowedRooms = rooms.filter(({id}) => !roomIdsHasThisUser.has(id))
            for (let room of sample_n(allowedRooms, numRequests)) {
                joinRoomRequests.push({
                    roomId: room.id,
                    userId: user.id,
                    status: sample([0, 1, 2]),
                    requestDate: randomDate()
                })
            }
        }
        return joinRoomRequests
    }
    function initSmallTransactions(room_user, MAX_NUM_SMALL_TRANSCATION_PER_USER_PER_ROOM) {
        let itemNames1 = ['Kem đánh răng', 'Cá', 'Mực', 'Giấy vệ sinh', 'Đậu phụ', 'Gạo', 'Kẹo mút',
            'Nem chua', 'Súng', 'Nước tương', 'Cà phê', 'Rau muống', 'Rau xà lách', 'Lạc', 'Ngô',
            'Thịt ba chỉ', 'Nước mắm', 'Chuối', 'Cá khô', 'Túi đựng rác']
        let itemNames2 = ['chợ Bách Khoa', 'chợ Long Biên', 'chợ Đồng Xuân', 'cửa hàng tạp hóa', 'vinmart', '', '']
        let notes = [
            'Đồ ăn hàng ngày',
            'Đồ dùng chung',
            'Khác'
        ]
        let smallTransactions = []
        let mapRoomIdToUserIds = {}
        for (let {roomId, userId, joinDate} of room_user) {
            if (!mapRoomIdToUserIds[roomId]) {
                mapRoomIdToUserIds[roomId] = []
            }
            mapRoomIdToUserIds[roomId].push([userId, joinDate])
        }
        for (let roomId in mapRoomIdToUserIds) {
            for (let [userId, joinDate] of mapRoomIdToUserIds[roomId]) {
                let numSTOfUserInRoom = randInt(0, MAX_NUM_SMALL_TRANSCATION_PER_USER_PER_ROOM)
                for (let i = 0; i < numSTOfUserInRoom; i++) {
                    let id = randomId()
                    let itemName = [sample(itemNames1), sample(itemNames2)].join(' ')
                    let price = randInt(10, 200)
                    let note = sample(notes)
                    smallTransactions.push({
                        id, userId, roomId,
                        itemName, price, note,
                        transactionDate: randomDate(joinDate)
                    })
                }
            }
        }
        return smallTransactions
    }
    function initFeeWithDeadline(room_user, MAX_NUM_FEE_WITH_DEADLINE) {
        let feeNames = [
            'Tiền điện',
            'Tiền nhà',
            'Tiền mừng cưới',
            'Tiền thuê phòng',
            'Tiền sửa bồn cầu',
            'Tiền sửa mái nhà'
        ]

        let user_feeWithDeadline = []
        let feesWithDealine = []

        let mapRoomIdToUserIds = {}
        for (let {roomId, userId, joinDate, status} of room_user) {
            if (!mapRoomIdToUserIds[roomId]) {
                mapRoomIdToUserIds[roomId] = []
            }
            mapRoomIdToUserIds[roomId].push([userId, joinDate, status])
        }

        for (let roomId in mapRoomIdToUserIds) {
            let numFees = randInt(1, MAX_NUM_FEE_WITH_DEADLINE)
            for (let i = 0; i < numFees; i++) {
                let id = randomId()
                let name = sample(feeNames)
                let deadline = randomDate()
                let numUsers = Math.max(1, mapRoomIdToUserIds[roomId].length - randInt(0, 2))
                let userIds = sample_n(mapRoomIdToUserIds[roomId], numUsers).map(x => x[0])
                
                for (let userId of userIds) {
                    user_feeWithDeadline.push({
                        userId,
                        feeId: id,
                        status: (random() < 0.7) ? 1 : 0
                    })
                }

                feesWithDealine.push({
                    id,
                    name, deadline,
                    roomId,
                    price: randInt(20, 60)*50
                })
            }
        }
        return [feesWithDealine, user_feeWithDeadline]
    }
    
    let users = initUsers(NUM_USERS)
    if (helpTrueAPI) {
        localStorage.setItem('userId', users[1].id)
    }
    let securityQuestions = initSecurityQues(users, MAX_NUM_SECURITY_QUESTIONS_PER_USER)
    let [rooms, room_user] = initRooms(users, NUM_ROOMS, MAX_NUM_MEMBERS_PER_ROOM)
    let joinRoomRequests = initJoinReq(users, rooms, room_user, MAX_NUM_JOIN_ROOM_REQUESTS_PER_USER)
    let smallTransactions = initSmallTransactions(room_user, MAX_NUM_SMALL_TRANSCATION_PER_USER_PER_ROOM)
    let [feesWithDealine, user_feeWithDeadline] = initFeeWithDeadline(room_user, MAX_NUM_FEE_WITH_DEADLINE)
    
    let generateSQLScript = false
    if (generateSQLScript) {
        let tables = [
            ['user', users, {
                id: '',
                avatar_url: '_',
                bank_account_number: 'bankNumber',
                bank_name: '_',
                name: 'fullname',
                phone_number: '_',
                username: '',
                password: ''
            }, {
                transform: {
                    password: (x) => '$2a$10$eSWWQ94PQkhwN7t5F1Bgxe06oyZBYkhYXUDYSDbW7c.Bw/5aAejXm'
                }
            }],
            ['security_question', securityQuestions, {
                id: '',
                question: '',
                answer: '',
                user_id: '_'
            }],
            ['room', rooms, {
                id: '',
                address: '',
                admin_id: 'adminUserId',
                name: 'roomName'
            }],
            ['member_of_room', room_user, {
                room_id: '_',
                status: '',
                user_id: '_',
                join_date: '_',
                out_date: 'leaveDate'
            }, {
                transform: {
                    join_date: CustomDateManager.toDBFormat,
                    out_date: CustomDateManager.toDBFormat
                }
            }],
            ['join_room_request', joinRoomRequests, {
                room_id: '_',
                status: '',
                user_id: '_',
                request_date: '_'
            }, {
                transform: {
                    request_date: CustomDateManager.toDBFormat
                }
            }],
            ['small_transaction', smallTransactions, {
                id: '',
                item_name: '_',
                price: '',
                room_id: '_',
                user_id: '_',
                transaction_time: 'transactionDate'
            }, {
                transform: {
                    transaction_time: CustomDateManager.toDBFormat
                }
            }],
            ['fee_with_deadline', feesWithDealine, {
                id: '',
                deadline: '',
                fee_name: 'name',
                money: 'price',
                room_id: '_'
            }, {
                transform: {
                    deadline: CustomDateManager.toDBFormat
                }
            }],
            ['user_fee_with_deadline', user_feeWithDeadline, {
                fee_id: '_',
                user_id: '_',
                status: ''
            }]
        ]

        let subScripts = ['use quanlynhatro;']

        tables.forEach(table => {
            let [tableName, container, mapFromTableKeyToContainerKey, options] = table
            let dbKeys = Object.keys(mapFromTableKeyToContainerKey)

            let line1Pattern = 'INSERT INTO `tableName` (keys) VALUES'
            let line1 = line1Pattern.replace('tableName', tableName)
            line1 = line1.replace('keys', dbKeys.map(k => '`' + k + '`').join(', '))

            let lineValuePattern = '(valuesForEachKey)'
            let valueLines = []
            container.forEach(row => {
                let toReplace = dbKeys.map(dbKey => {
                    let corrKey = mapFromTableKeyToContainerKey[dbKey]
                    if (corrKey == '') corrKey = dbKey
                    else if (corrKey == '_') {
                        let paths = dbKey.split('_')
                        for (let i = 1; i < paths.length; i++) {
                            let path_elems = paths[i].split('')
                            if (path_elems.length > 0) path_elems[0] = path_elems[0].toUpperCase()
                            paths[i] = path_elems.join('')
                        }
                        corrKey = paths.join('')
                    }
                    let originalValue = row[corrKey]
                    if (originalValue != undefined) {
                        let transformFunc = options?.transform?.[dbKey]
                        let transformedValue = transformFunc ? transformFunc(originalValue) : originalValue
                        if (!TypeManager.isNumeric(transformedValue)) transformedValue = `'${transformedValue}'`
                        return transformedValue
                    }
                    return 'NULL'
                }).join(', ')
                valueLines.push(lineValuePattern.replace('valuesForEachKey', toReplace))
            })

            let tableScript = line1 + '\n' + valueLines.join(',\n') + ';'
            subScripts.push(tableScript)
        })

        let finalData = subScripts.join('\n\n\n')
        let link = document.createElement("a")
        let file = new Blob([finalData], { type: 'text/plain' })
        link.href = URL.createObjectURL(file)
        link.download = 'quanlynhatro-script.sql'
        link.click()
        URL.revokeObjectURL(link.href)
    }

    let userDAO = (() => {
        return {
            findUserByUsername: (un) => users.find(user => user.username == un),
            findUserById: (id) => users.find(user => user.id == id),
            saveUser: (user) => users.push(user),
            getRoomIdsOfUserId: (uid) => [...new Set(room_user.filter(({userId}) => userId == uid).map(({roomId}) => roomId))],
            getUsersOfRoomId: (rid, _status = 1) => {
                let userIds = new Set(room_user.filter(({roomId, status}) => roomId == rid && (!_status || _status == status)).map(({userId}) => userId))
                return users.filter(({id}) => userIds.has(id))
            }
        }
    })()
    let roomDAO = (() => {
        return {
            createRoom: (name, address, adminUserId) => {
                let newRoom = {
                    id: randomId(),
                    roomName: name,
                    adminUserId,
                    address
                }
                rooms.push(newRoom)

                room_user.push({
                    userId: adminUserId,
                    roomId: newRoom.id,
                    status: 1,
                    joinDate: CustomDateManager.now()
                })

                return newRoom
            },
            getRoomsOfUserId: (uid) => {
                let roomIdsOfUser = new Set(room_user.filter(({userId, status}) => uid == userId && status == 1).map(({roomId}) => roomId))
                return rooms.filter(room => roomIdsOfUser.has(room.id))
            },
            getUsersEarlyThisMonth: (rid) => {
                let earlyThisMonth = `${1}/${new Date().getMonth() + 1}/${new Date().getFullYear()}`
                let pairs = room_user.filter(({userId, roomId, status, leaveDate}) => {
                    return roomId == rid
                    && (status == 1 || !CustomDateManager.d1SmallerThanD2(leaveDate, earlyThisMonth))
                })
                return pairs.map(({userId}) => userDAO.findUserById(userId))
            },
            getNumUsersEarlyThisMonth: (rid) => {
                return roomDAO.getUsersEarlyThisMonth(rid).length
            },
            getUsersEarlyOfMonthAndYear: (rid, month, year) => {
                let earlyThatMonth = `${1}/${month}/${year}`
                let pairs = room_user.filter(({userId, roomId, status, leaveDate}) => {
                    return roomId == rid
                    && (status == 1 || !CustomDateManager.d1SmallerThanD2(leaveDate, earlyThatMonth))
                })
                return pairs.map(({userId}) => userDAO.findUserById(userId))
            },
            getNumUsersEarlyOfMonthAndYear: (rid, month, year) => {
                return roomDAO.getUsersEarlyOfMonthAndYear(rid, month, year).length
            },
            getRoomById: (rid) => rooms.find(({id}) => rid == id)
        }
    })()
    let smallTransactionsDAO = (() => {
        return {
            getSmallTransaction: (rid, month, year, specificUserId) => {
                month = Number(month)
                year = Number(year)
                return smallTransactions.filter(({userId, roomId, transactionDate}) => {
                    let [d, m, y] = transactionDate.split('/').map(x => Number(x))
                    return (roomId == rid) && (month == m) && (year == y)
                        && (specificUserId ? (specificUserId == userId) : true)
                })
            },
            createSmallTransaction: (itemName, price, date, roomId, userId) => {
                let newItem = {
                    id: randomId(),
                    transactionDate: date,
                    userId, roomId,
                    itemName, price, note: ''
                }
                smallTransactions.push(newItem)
                return newItem
            },
            update: (itemId, itemName, price, date) => {
                let item = smallTransactions.find(({id}) => id == itemId)
                item.itemName = itemName
                item.price = price
                item.transactionDate = date
                return item
            },
            delete: (itemId) => {
                let index = smallTransactions.findIndex(({id}) => id == itemId)
                smallTransactions.splice(index, 1)
            }
        }
    })()
    let feesWithDealineDAO = (() => {
        return {
            get: (fid) => {
                return feesWithDealine.find(({id}) => id == fid)
            },
            getFeesWithDeadline: (rid) => {
                return feesWithDealine.filter(({roomId}) => roomId == rid)
            }
        }
    })()
    let joinRoomReqDAO = (() => {
        return {
            get: (rid, uid = null, _status = null, all = false) => joinRoomRequests[all ? 'filter' : 'find'](({roomId, userId, status}) => {
                return roomId == rid
                    && (!uid || uid == userId)
                    && (!_status || _status == status)
            })
        }
    })()

    let a = {
        login: function (username, password, {onDone, onFailed}) {
            let user = userDAO.findUserByUsername(username)
            if (!user) {
                onFailed(ERROR.USERNAME_DOESNOT_EXIST)
            }
            else if (user.password != password) {
                onFailed(ERROR.WRONG_PASSWORD)
            }
            else {
                localStorage.setItem('userId', user.id)
                onDone()
            }
        },
        logout: function ({onDone, onFailed}) {
            localStorage.removeItem('userId')
            onDone()
        },
        signUp: function (fullname, username, password, {onDone, onFailed}) {
            let user = userDAO.findUserByUsername(username)
            if (user) {
                onFailed(ERROR.EXISTED_USERNAME)
            }
            let newUser = {
                username,
                fullname,
                password
            }
            userDAO.saveUser(newUser)
            localStorage.setItem('userId', user.id)
            onDone()
        },
        getUserInfo: ({onDone, onFailed}) => {
            let userIdStorage = localStorage.getItem('userId')
            let user = userDAO.findUserById(userIdStorage)
            if (!user) {
                onFailed('Not logined!')
                return
            }
            onDone(user)
        },
        getRoomsOfUser: ({onDone, onFailed}) => {
            let userId = localStorage.getItem('userId')
            onDone(roomDAO.getRoomsOfUserId(userId).map(room => {
                room.isAdmin = room.adminUserId == userId
                return room
            }))
        },
        getSmallTransaction: (rid, month, year, specificUserId, {onDone, onFailed}) => {
            onDone(smallTransactionsDAO.getSmallTransaction(rid, month, year, specificUserId))
        },
        createSmallTransaction: (name, price, date, roomId, {onDone}) => {
            let uid = localStorage.getItem('userId')
            onDone(smallTransactionsDAO.createSmallTransaction(name, price, date, roomId, uid))
        },
        updateSmallTransaction: (itemId, name, price, date, {onDone}) => {
            let uid = localStorage.getItem('userId')
            onDone(smallTransactionsDAO.update(itemId, name, price, date))
        },
        deleteSmallTransaction: (itemId, {onDone}) => {
            let uid = localStorage.getItem('userId')
            smallTransactionsDAO.delete(itemId)
            onDone()
        },
        getUsersOfRoomId: (roomId, {onDone, onFailed}) => {
            onDone(userDAO.getUsersOfRoomId(roomId))
        },
        getQuickStatisticInfo: (rid, {onDone, onFailed}) => {
            let uid = localStorage.getItem('userId')
            let smTrans = smallTransactionsDAO.getSmallTransaction(rid, new Date().getMonth() + 1, new Date().getFullYear(), null)
            let roomSpent = smTrans.reduce((pv, {price}) => pv + Number(price), 0)
            let mySmTrans = smTrans.filter(({userId}) => userId == uid)
            let mySpent = mySmTrans.reduce((pv, {price}) => pv + Number(price), 0)
            let numUsers = roomDAO.getNumUsersEarlyThisMonth(rid)
            onDone({
                mySpent,
                roomAverage: Math.round(roomSpent/numUsers)
            })
        },
        createFeesWithDeadline: (rid, name, price, deadline, {onDone}) => {
            let newFee = {
                id: randomId(),
                name, deadline, price,
                roomId: rid
            }
            feesWithDealine.push(newFee)
            let users = userDAO.getUsersOfRoomId(rid)
            users.forEach(({id}) => {
                user_feeWithDeadline.push({
                    userId: id,
                    feeId: newFee.id,
                    status: 0
                })
            })
            onDone()
        },
        getFeesWithDeadline: (rid, {onDone}) => {
            onDone(feesWithDealineDAO.getFeesWithDeadline(rid))
        },
        getPayFeeWDStatus: (rid, allUser, {onDone}) => {
            if (!allUser) {
                let uid = localStorage.getItem('userId')
                let feeWD = feesWithDealineDAO.getFeesWithDeadline(rid)
                let feeIds = new Set(feeWD.map(({id}) => id))
                let pairs = user_feeWithDeadline.filter(({feeId, userId}) => feeIds.has(feeId) && userId == uid)
                pairs = pairs.map(p => {
                    let fee = feeWD.find(({id}) => id == p.feeId)
                    p.feeName = fee.name
                    p.pricePerUser = Math.ceil(fee.price/user_feeWithDeadline.filter(({feeId}) => feeId == fee.id).length)
                    return p
                })
                onDone(pairs)
                return
            }
            let feeWD = feesWithDealineDAO.getFeesWithDeadline(rid)
            let feeIds = new Set(feeWD.map(({id}) => id))
            let idUsersInRoom = new Set(userDAO.getUsersOfRoomId(rid).map(({id}) => id))
            let allUsersHasBeenInRoom = userDAO.getUsersOfRoomId(rid, null)
            let payStatus = user_feeWithDeadline.filter(({feeId}) => feeIds.has(feeId))
            feeWD.forEach(f => {
                f.pricePerUser = Math.ceil(f.price/payStatus.filter(({feeId}) => feeId == f.id).length)
            })
            let data = {
                feesWithDealine: feeWD,
                payStatus,
                users: allUsersHasBeenInRoom.map(user => {
                    user.inRoom = idUsersInRoom.has(user.id)
                    return user
                })
            }
            onDone(data)
        },
        changePayFeeWDStatus: (uid, fid, {onDone}) => {
            let pair = user_feeWithDeadline.find(({feeId, userId}) => feeId == fid && uid == userId)
            pair.status = !pair.status
            onDone()
        },
        deleteFeeWithDeadline: (fid, {onDone}) => {
            feesWithDealine = feesWithDealine.filter(({id}) => id != fid)
            user_feeWithDeadline = user_feeWithDeadline.filter(({feeId}) => feeId != fid)
            onDone()
        },
        updateFeeWithDeadline: (fid, name, price, deadline, {onDone}) => {
            let fee = feesWithDealineDAO.get(fid)
            Object.assign(fee, {name, price, deadline})
            onDone(fee)
        },
        createRoom: (name, address, {onDone}) => {
            let uid = localStorage.getItem('userId')
            onDone(roomDAO.createRoom(name, address, uid))
        },
        createJoinRoomRequest: (roomId, {onDone, onFailed}) => {
            let uid = localStorage.getItem('userId')
            let room = roomDAO.getRoomById(roomId)
            if (!room) {
                onFailed(ERROR.ROOM_ID_DOESNOT_EXIST)
                return
            }
            let roomsOfUser = roomDAO.getRoomsOfUserId(uid)
            if (roomsOfUser.some(({id}) => id == roomId)) {
                onFailed(ERROR.USER_HAS_BEEN_IN_ROOM)
                return
            }
            let joinReqExisted = joinRoomReqDAO.get(roomId, uid)
            if (joinReqExisted) {
                joinReqExisted.status = 1
                joinReqExisted.requestDate = CustomDateManager.now()
                onDone()
                return
            }
            let joinRoomRequest = {
                userId, roomId,
                status: 1,
                requestDate: CustomDateManager.now()
            }
            joinRoomRequests.push(joinRoomRequest)
            onDone()
        },
        getJoinRoomRequestOfUser: ({onDone, onFailed}) => {
            let uid = localStorage.getItem('userId')
            let joinReqs = joinRoomRequests.filter(({userId}) => userId == uid)
            joinReqs.forEach(joinReq => {
                joinReq.roomName = roomDAO.getRoomById(joinReq.roomId).roomName
            })
            onDone(joinReqs)
        },
        getJoinRoomRequestOfRoom: (roomId, {onDone, onFailed}) => {
            let joinReqs = joinRoomReqDAO.get(roomId, null, 1, true)
            joinReqs.forEach(req => {
                let user = userDAO.findUserById(req.userId)
                Object.assign(req, {
                    fullname: user.fullname,
                    avatarUrl: user.avatarUrl
                })
            })
            onDone(joinReqs)
        },
        cancelJoinRoomRequest: (roomId, {onDone}) => {
            let uid = localStorage.getItem('userId')
            let joinReq = joinRoomReqDAO.get(roomId, uid)
            joinReq.status = 3
            onDone()
        },
        acceptJoinRoomRequest: (rid, uid, accept, {onDone}) => {
            if (accept) {
                let joinReq = joinRoomReqDAO.get(rid, uid)
                joinReq.status = 2
                let pair = room_user.find(({userId, roomId}) => userId == uid && roomId == rid)
                if (!pair) {
                    pair = {
                        userId: uid,
                        roomId: rid
                    }
                    room_user.push(pair)
                }
                Object.assign(pair, {
                    status: 1,
                    joinDate: CustomDateManager.now()
                })
            }
            else {
                let joinReq = joinRoomReqDAO.get(rid, uid)
                joinReq.status = 0
            }
            onDone()
        },
        allRoomsStatistic: (month, year, {onDone}) => {
            let uid = localStorage.getItem('userId')
            let roomIds = userDAO.getRoomIdsOfUserId(uid)
            let result = []
            roomIds.forEach(rid => {
                let total = smallTransactionsDAO.getSmallTransaction(rid, month, year).reduce((prev, {price}) => prev + price, 0)
                total += feesWithDealineDAO.getFeesWithDeadline(rid).filter(({deadline}) => {
                    let [d, m, y] = deadline.split('/').map(x => Number(x))
                    return m == month && y == year
                }).reduce((prev, {price}) => prev + price, 0)
                total = Math.round(total/roomDAO.getNumUsersEarlyThisMonth(rid))
                result.push({
                    roomName: roomDAO.getRoomById(rid).roomName,
                    totalSpending: total
                })
            })
            onDone(result)
        },
        totalSpendingEachMonthOfYear: (year, {onDone}) => {
            let uid = localStorage.getItem('userId')
            let resultTemp = {}
            let roomIds = new Set(userDAO.getRoomIdsOfUserId(uid))
            smallTransactions.forEach(({roomId, price, transactionDate}) => {
                if (!roomIds.has(roomId)) return

                let [d, m, y] = transactionDate.split('/').map(x => Number(x))
                if (y != year) return

                price = Number(price)

                let key = roomId + '--' + m
                if (resultTemp[key]) resultTemp[key] += price
                else resultTemp[key] = price
            })
            feesWithDealine.forEach(({deadline, roomId, price}) => {
                if (!roomIds.has(roomId)) return

                let [d, m, y] = deadline.split('/').map(x => Number(x))
                if (y != year) return

                price = Number(price)
                let key = roomId + '--' + m
                if (resultTemp[key]) resultTemp[key] += price
                else resultTemp[key] = price
            })
            let numMembers = {}
            roomIds.forEach(rid => {
                range(1, 13).forEach(m => {
                    numMembers[rid + '--' + m] = roomDAO.getNumUsersEarlyOfMonthAndYear(rid, m, year)
                })
            })
            let result = []
            range(1, 13).forEach(month => {
                let t = 0
                for (let rid of roomIds) {
                    t += (resultTemp[rid + '--' + month] || 0)/numMembers[rid + '--' + month]
                }
                result.push(Math.ceil(t))
            })
            onDone(result)
        },
        changeAdmin: (data, onDone, onFailed) => {
            let {roomId, newAdminId} = data
            roomDAO.getRoomById(roomId).adminUserId = newAdminId
            onDone()
        },
        leaveRoom: (data, onDone, onFailed) => {
            let {roomId} = data
            let rid = roomId
            let uid = localStorage.getItem('userId')
            let room = roomDAO.getRoomById(roomId)
            if (uid == room.adminUserId) {
                if (userDAO.getUsersOfRoomId(roomId).length != 1) {
                    onFailed(ERROR.ROOM_ADMIN_CAN_NOT_LEAVE_ROOM)
                    return
                }
            }
            let pair = room_user.find(({userId, roomId}) => userId == uid && roomId == rid)
            pair.status = 0
            pair.leaveDate = CustomDateManager.now()
            onDone()
        },
        changePassword: (data, onDone, onFailed) => {
            let {newPassword, oldPassword} = data
            let uid = localStorage.getItem('userId')
            let user = userDAO.findUserById(uid)
            if (user.password != oldPassword) {
                onFailed(ERROR.WRONG_PASSWORD)
                return
            }
            user.password = newPassword
            onDone()
        },
        updateProfile: (data, onDone, onFailed) => {
            let uid = localStorage.getItem('userId')
            let user = userDAO.findUserById(uid)
            Object.assign(user, data)
            onDone()
        },
        getSecurityQuestions: (onDone, onFailed) => {
            let uid = localStorage.getItem('userId')
            onDone(securityQuestions.filter(({userId}) => userId == uid).map(q => {
                let r = {}
                for (let k in q) if (k != 'answer') r[k] = q[k]
                return r
            }))
        },
        deleteSecurityQuestion: (data, onDone, onFailed) => {
            let uid = localStorage.getItem('userId')
            let {id} = data
            let qid = id
            securityQuestions = securityQuestions.filter(({id}) => qid != id)
            onDone()
        },
        addSecurityQuestion: (data, onDone, onFailed) => {
            let uid = localStorage.getItem('userId')
            let {question, answer, password} = data
            let user = userDAO.findUserById(uid)
            if (user.password != password) {
                onFailed(ERROR.WRONG_PASSWORD)
                return
            }
            securityQuestions.push({
                id: randomId(),
                userId: uid,
                question, answer
            })
            onDone()
        },
        removeMember: (data, onDone, onFailed) => {
            let {roomId, userId} = data
            let rid = roomId, uid = userId
            let pair = room_user.find(({roomId, userId}) => roomId == rid && userId == uid)
            Object.assign(pair, {
                status: 0,
                leaveDate: CustomDateManager.now()
            })
            onDone()
        },
        roomSmallTransactionPrevMonthStatistic: (data, onDone, onFailed) => {
            let {roomId} = data
            let now = {
                month: new Date().getMonth() + 1,
                year: new Date().getFullYear()
            }
            let prevMonth = {
                month: now.month == 1 ? 12 : now.month - 1,
                year: now.month == 1 ? now.year - 1 : now.year
            }
            let prevMonthTrans = smallTransactionsDAO.getSmallTransaction(roomId, prevMonth.month, prevMonth.year)
            let prevMonthUsers = roomDAO.getUsersEarlyOfMonthAndYear(roomId, prevMonth.month, prevMonth.year)
            let userCount = OCPHS(prevMonthUsers, (user) => [user.id, 0])
            let total = 0
            prevMonthTrans.forEach(({userId, price}) => {
                userCount[userId] += price
                total += price
            })
            let result = {
                total,
                average: Math.ceil(total/prevMonthUsers.length),
                memberSpendings: prevMonthUsers.map(user => {
                    return {
                        fullname: user.fullname,
                        spend: userCount[user.id]
                    }
                })
            }
            setTimeout(() => onDone(result), API_DELAY)
        },
        getSecurityQuestionsByUsername: (data, onDone, onFailed) => {
            let {username} = data
            let user = userDAO.findUserByUsername(username)
            if (!user) {
                setTimeout(() => onFailed(ERROR.USERNAME_DOESNOT_EXIST), API_DELAY)
                return
            }
            let securityQuestionsOfUser = securityQuestions.filter(({userId}) => userId == user.id).map(q => {
                let r = {}
                for (let k in q) if (k != 'answer') r[k] = q[k]
                return r
            })
            if (securityQuestionsOfUser.length == 0) {
                setTimeout(() => onFailed(ERROR.USER_DOESNOT_HAVE_SECURITY_QUESTION), API_DELAY)
                return
            }
            setTimeout(() => onDone(securityQuestionsOfUser), API_DELAY)
        },
        answerSecurityQuestion: (data, onDone, onFailed) => {
            let {questionId, answer, newPassword} = data
            let sQ = securityQuestions.find(({id}) => id == questionId)
            if (!sQ) {
                setTimeout(() => onFailed(ERROR.ERROR), API_DELAY)
                return
            }
            if (sQ.answer != answer) {
                setTimeout(() => onFailed(ERROR.WRONG_ANSWER_SECURITY_QUESTION), API_DELAY)
                return
            }
            userDAO.findUserById(sQ.userId).password = newPassword
            onDone()
        },
        updateAvatarUrl: (data, onDone, onFailed) => {
            let uid = localStorage.getItem('userId')
            userDAO.findUserById(uid).avatarUrl = data.avatarUrl
            setTimeout(() => onDone(), API_DELAY)
        }
    }
    return a
}

let fakeApi = FakeAPI(true)

function TrueAPI() {
    let base = '.'
    let DEV = true
    function defaultOnFailed() {
        popUpMessage('Có lỗi xảy ra!')
    }
    function $ajax({url, data, onDone, onFailed, type, json}) {
        type = (type || 'get').toUpperCase()
        data = data || {}
        if (type != 'GET') data = JSON.stringify(data)
        if (url[0] != '/') url = '/' + url
        $.ajax({
            type,
            url: base + url,
            contentType: "application/json",
            dataType: json ? 'json' : 'text',
            data,
            success: (result, status, xhr) => {
                if (DEV) l(`"${url}"`, result)
                if (onDone) onDone(result)
            },
            error: (xhr, status, error) => {
                if (DEV) l(`"${url}"`, xhr.responseText)
                if (onFailed) onFailed(xhr.responseText)
                else {
                    defaultOnFailed()
                }
            }
        })
    }
    let a = {
        getUserInfo: ({onDone, onFailed}) => {
            $ajax({
                url: '/info',
                onFailed,
                onDone,
                json: true
            })
        },
        login: function (username, password, {onDone, onFailed}) {
            $ajax({
                url: '/login',
                data: {username, password},
                onFailed,
                onDone,
                type: 'post'
            })
        },
        signUp: (fullname, username, password, {onDone, onFailed}) => {
            $ajax({
                url: '/signup',
                data: {fullname, username, password},
                onDone,
                onFailed,
                type: 'post'
            })
        },
        logout: ({onDone, onFailed}) => {
            $ajax({
                url: '/logout2',
                onDone,
                onFailed
            })
        },
        getRoomsOfUser: ({onDone, onFailed}) => {
            $ajax({
                url: '/room/listRoom',
                onDone,
                onFailed,
                json: true
            })
        },
        allRoomsStatistic: (month, year, {onDone, onFailed}) => {
            // fakeApi.allRoomsStatistic(month, year, {onDone, onFailed})
        },
        totalSpendingEachMonthOfYear: (year, {onDone, onFailed}) => {
            // fakeApi.totalSpendingEachMonthOfYear(year, {onDone, onFailed})
        },
        getSmallTransaction: (rid, month, year, specificUserId, {onDone, onFailed}) => {
            let pV = ['', rid, year, month]
            if (specificUserId) pV.push(specificUserId)
            let onDoneBackup = onDone
            onDone = (p) => {
                for (let sT of p) {
                    sT.transactionDate = CustomDateManager.fromDBFormat(sT.transactionTime)
                }
                onDoneBackup(p)
            }
            $ajax({
                url: '/transaction/getByRoomId' + pV.join('/'),
                onDone, onFailed,
                type: 'get',
                json: true
            })
        },
        createSmallTransaction: (name, price, date, roomId, {onDone, onFailed}) => {
            let onDoneBackup = onDone
            onDone = (p) => {
                p.transactionDate = CustomDateManager.fromDBFormat(p.transactionTime)
                onDoneBackup(p)
            }
            $ajax({
                url: '/transaction/create',
                data: {
                    name, price, roomId,
                    date: CustomDateManager.toDBFormat(date)
                },
                onDone, onFailed,
                type: 'post',
                json: true
            })
        },
        updateSmallTransaction: (itemId, name, price, date, {onDone, onFailed}) => {
            let onDoneBackup = onDone
            onDone = (p) => {
                p.transactionDate = CustomDateManager.fromDBFormat(p.transactionTime)
                onDoneBackup(p)
            }
            $ajax({
                url: '/transaction/update?id=' + itemId,
                data: {
                    name, price, itemId,
                    date: CustomDateManager.toDBFormat(date)
                },
                onDone, onFailed,
                type: 'put',
                json: true
            })
        },
        deleteSmallTransaction: (itemId, {onDone, onFailed}) => {
            $ajax({
                url: '/transaction/delete/' + itemId,
                onDone, onFailed,
                type: 'delete'
            })
        },
        getUsersOfRoomId: (roomId, {onDone, onFailed}) => {
            $ajax({
                url: '/room/memberOfRoom',
                data: { roomId },
                onDone, onFailed,
                json: true
            })
        },
        getQuickStatisticInfo: (rid, {onDone, onFailed}) => {
            $ajax({
                url: '/transaction/getStatusMoney',
                onDone, onFailed,
                data: {roomId: rid},
                json: true
            })
        },
        createFeesWithDeadline: (rid, name, price, deadline, {onDone, onFailed}) => {
            $ajax({
                url: '/feewd/create',
                data: {
                    roomId: rid,
                    deadline: CustomDateManager.toDBFormat(deadline),
                    name, price
                },
                onDone, onFailed,
                type: 'post'
            })
        },
        getFeesWithDeadline: (rid, {onDone, onFailed}) => {
            let onDoneBackup = onDone
            onDone = (fs) => {
                for (let f of fs) {
                    f.deadline = CustomDateManager.fromDBFormat(f.deadline)
                }
                onDoneBackup(fs)
            }
            $ajax({
                url: '/feewd/getByRoomId/' + rid,
                onDone, onFailed,
                json: true
            })
        },
        getPayFeeWDStatus: (rid, allUser, {onDone, onFailed}) => {
            if (allUser) {
                let onDoneBackup = onDone
                onDone = (p) => {
                    function x(fs) {
                        let eSign = new Set()
                        let pC = p.map(sp => {
                            let c = {}
                            for (let k in sp) c[k] = sp[k]
                            return c
                        })
                        return pC.filter(sp => {
                            let sign = fs.map(f => sp[f] || 'abc').join('--')
                            if (!eSign.has(sign)) {
                                eSign.add(sign)
                                return true
                            }
                            return false
                        })
                    }
                    let users = x(['userId'])
                    users.forEach(u => {
                        u.inRoom = u.inRoomStatus
                        u.id = u.userId
                    })
                    let feesWithDealine = x(['feeId'])
                    feesWithDealine.forEach(f => {
                        f.name = f.feeName
                        f.id = f.feeId
                        f.deadline = CustomDateManager.fromDBFormat(f.deadline)
                    })
                    let payStatus = x(['feeId', 'userId'])
                    payStatus.forEach(ps => {
                        ps.status = ps.payStatus
                    })
                    onDoneBackup({users, feesWithDealine, payStatus})
                }
                $ajax({
                    url: '/feewd/roomStatusFeeWD',
                    data: {roomId: rid},
                    onDone, onFailed,
                    json: true
                })
            }
            else {
                let onDoneBackup = onDone
                onDone = (rs) => {
                    rs.forEach(r => r.deadline = CustomDateManager.fromDBFormat(r.deadline))
                    onDoneBackup(rs)
                }
                $ajax({
                    url: '/feewd/userStatusFeeWD',
                    data: {roomId: rid},
                    onDone, onFailed,
                    json: true
                })
            }
        },
        changePayFeeWDStatus: (uid, fid, {onDone, onFailed}) => {
            $ajax({
                url: '/feewd/reverseStatus/' + uid + '/' + fid,
                onDone, onFailed,
                type: 'patch'
            })
        },
        deleteFeeWithDeadline: (fid, {onDone, onFailed}) => {
            $ajax({
                url: '/feewd/delete/' + fid,
                onDone, onFailed,
                type: 'delete'
            })
        },
        updateFeeWithDeadline: (fid, name, price, deadline, {onDone, onFailed}) => {
            $ajax({
                url: '/feewd/update/' + fid,
                onDone, onFailed,
                data: {
                    name, price,
                    deadline: CustomDateManager.toDBFormat(deadline)
                },
                type: 'put'
            })
        },
        createRoom: (name, address, {onDone, onFailed}) => {
            $ajax({
                url: '/room/create',
                data: {name, address},
                onDone, onFailed,
                type: 'post',
                json: true
            })
        },
        createJoinRoomRequest: (roomId, {onDone, onFailed}) => {
            $ajax({
                url: '/room/join',
                data: {roomId},
                onDone, onFailed,
                type: 'post'
            })
        },
        getJoinRoomRequestOfUser: ({onDone, onFailed}) => {
            let onDoneBackup = onDone
            onDone = (js) => {
                js.forEach(j => j.requestDate = CustomDateManager.fromDBFormat(j.requestDate))
                onDoneBackup(js)
            }
            $ajax({
                url: '/room/joinRoomRequests',
                onDone, onFailed,
                json: true
            })
        },
        getJoinRoomRequestOfRoom: (roomId, {onDone, onFailed}) => {
            let onDoneBackup = onDone
            onDone = (js) => {
                js.forEach(j => j.requestDate = CustomDateManager.fromDBFormat(j.requestDate))
                onDoneBackup(js)
            }
            $ajax({
                url: '/room/JRRForAdmin',
                data: {roomId},
                onDone, onFailed,
                json: true
            })
        },
        cancelJoinRoomRequest: (roomId, {onDone, onFailed}) => {
            $ajax({
                url: '/room/joinRoomRequests/cancel?roomId=' + roomId,
                onDone, onFailed,
                type : 'delete'
            })
        },
        acceptJoinRoomRequest: (rid, uid, accept, {onDone, onFailed}) => {
            $ajax({
                url: 'room/JRRForAdmin/approval',
                data: {
                    roomId: rid,
                    userId: uid,
                    accept
                },
                onDone, onFailed,
                type: 'put'
            })
        },
        changeAdmin: (data, onDone, onFailed) => {
            $ajax({
                url: '/room/switchAdmin',
                data, onDone, onFailed
            })
        },
        leaveRoom: (data, onDone, onFailed) => {
            $ajax({
                url: '/room/leaveRoom',
                data, onDone, onFailed
            })
        },
        changePassword: (data, onDone, onFailed) => {
            $ajax({
                url: '/change-password',
                type: 'post',
                data, onDone, onFailed
            })
        },
        updateProfile: (data, onDone, onFailed) => {
            $ajax({
                url: '/update',
                type: 'put',
                data, onDone, onFailed
            })
        },
        getSecurityQuestions: (onDone, onFailed) => {
            $ajax({
                url: '/security-question/get',
                onDone, onFailed,
                json: true
            })
        },
        deleteSecurityQuestion: (data, onDone, onFailed) => {
            $ajax({
                url: '/security-question/delete/' + data.id,
                onDone, onFailed,
                type: 'delete'
            })
        },
        addSecurityQuestion: (data, onDone, onFailed) => {
            $ajax({
                url: '/security-question/add',
                data, onDone, onFailed,
                type: 'post'
            })
        },
        removeMember: (data, onDone, onFailed) => {
            $ajax({
                url: '/room/memberOfRoom/deleteMember',
                data, onDone, onFailed
            })
        },
        roomSmallTransactionPrevMonthStatistic: (data, onDone, onFailed) => {
            $ajax({
                url: '/api/transactions/last_month',
                data, onDone, onFailed,
                type: 'post',
                json: 'true'
            })
        },
        getSecurityQuestionsByUsername: (data, onDone, onFailed) => {
            $ajax({
                url: '/security-question/get-by-username',
                data, onDone, onFailed,
                json: true
            })
        },
        answerSecurityQuestion: (data, onDone, onFailed) => {
            $ajax({
                url: '/security-question/answer-and-change-password',
                data, onDone, onFailed,
                type: 'post'
            })
        },
        updateAvatarUrl: (data, onDone, onFailed) => {
            $ajax({
                url: '/update_avatar',
                data, onDone, onFailed,
                type: 'put'
            })
        }
    }
    return a
}

api = TrueAPI()

