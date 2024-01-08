
let defaultAvatarUrl = "./resources/user-images/default-avatar.jpg"

let addTransactionFormLoaded = false
function loadInputTransactionView() {
    let jCont = $('#input-transaction')

    function reloadQuickInfo() {
        api.getQuickStatisticInfo(currentRoom.id, {
            onDone: ({ mySpent, roomAverage }) => {
                mySpent = Math.ceil(Number(mySpent)), roomAverage = Math.ceil(Number(roomAverage))
                let jElem = jCont.find('.left #money-spent')
                jElem.find('#my-spent .value')
                    .text(mySpent + 'k')
                    .toggleClass('value-green', mySpent <= roomAverage)
                    .toggleClass('value-red', mySpent > roomAverage)
                jElem.find('.room-average .value')
                    .text(roomAverage + 'k')
            }
        })
    }

    function checkSmallTransactionDate(date) {
        let error = null
        let [d, m, y] = date.split('/').map(x => Number(x))
        let now = new Date()
        let nowMonth = now.getMonth() + 1
        let nowYear = now.getFullYear()

        let weekDistance = y * 12 + m - nowYear * 12 - nowMonth
        if (weekDistance < 0) {
            error = 'Không thể thêm giao dịch vào các tháng trước đó, vui lòng sửa lại ngày giao dịch'
        }
        else if (weekDistance > 3) {
            error = 'Chỉ được thêm giao dịch vào tối đa 3 tháng tương lai, vui lòng sửa lại ngày giao dịch'
        }
        return error
    }

    function appendNewItemToListItem(newItem) {
        let jItems = jCont.find('#transactions .item')
        let jNewItem = createItemInListTransaction(newItem)

        let i = 0
        for (; i < jItems.length; i++) {
            let itemDate = jItems.eq(i).find('.info .user-bought').html().split(' - ')[1]
            if (CustomDateManager.d1SmallerThanD2(itemDate, newItem.transactionDate)) {
                break
            }
        }
        if (i != jItems.length) {
            jNewItem.insertBefore(jItems[i])
        } else {
            jCont.find('#transactions').append(jNewItem)
        }
    }

    // Add small transaction
    let jAddItemForm = jCont.find('.left form')
    jAddItemForm.find('input').val('')
    jAddItemForm.find('.errors').html('')
    if (!addTransactionFormLoaded) {
        addTransactionFormLoaded = true
        FormManager(jAddItemForm, {
            fieldNamesAndRequires: [
                { name: 'name', requires: 'notEmpty', vnName: 'Tên giao dịch' },
                { name: 'price', requires: ['notEmpty', 'isNonNegativeInt'], vnName: 'Số tiền' },
                {
                    name: 'date', requires: ['notEmpty', 'isMyCustomDate', (val, errors) => {
                        let error = checkSmallTransactionDate(val)
                        if (!error) return true
                        errors.push(error)
                        return false
                    }],
                    vnName: 'Ngày giao dịch'
                }
            ],
            onSubmit: (formData) => {
                let newItemInListTrasnView = false
                let newItemInThisMonth = false
                let [d, m, y] = formData['date'].split('/').map(x => Number(x))
                if (m == currMonth && y == currYear) {
                    newItemInListTrasnView = true
                }
                let now = new Date()
                let nowMonth = now.getMonth() + 1
                let nowYear = now.getFullYear()
                if (m == nowMonth && y == nowYear) {
                    newItemInThisMonth = true
                }
    
                let name = formData.name
                let price = formData.price
                let date = formData.date
    
                api.createSmallTransaction(name, price, date, currentRoom.id, {
                    onDone: (newItem) => {
                        newItem.fullname = user.fullname
                        jAddItemForm.find('.errors').html('')
                        jAddItemForm.find('input').val('')
                        if (newItemInThisMonth) reloadQuickInfo()
                        if (!newItemInListTrasnView) return
    
                        appendNewItemToListItem(newItem)
                    }
                })
            }
        })
    }

    jCont.find('.user-selection').html('')
    api.getUsersOfRoomId(currentRoom.id, {
        onDone: (users) => {
            jCont.find('.user-selection')
                .html(
                    `<option value="">Mọi người</option>`
                    + users.map(({ fullname, id }) => `<option value="${id}">${fullname}</option>`).join('')
                )
                .on('change', loadListTransaction)
        }
    })

    function createItemInListTransaction({ id, itemName, userId, price, transactionDate, fullname }) {
        let editable = (userId == user.id) && (transactionDate.split('/')[1] == new Date().getMonth() + 1)
        let itemHtml = (`<div class="item">
            <div class="info">
                <div class="product-name">${itemName}</div>
                <div class="user-bought">${fullname} - ${transactionDate}</div>
            </div>

            ${editable ? `<div class="more-operation">
                <img class="icon" src="./resources/three-dot.png">
            </div>` : ''}

            <div class="cost">${price}k</div>
        </div>`)

        let fEHtml = `
            <div class="edit op">Sửa</div>
            <div class="delete op">Xóa</div>
        `

        let jElem = $(itemHtml)

        if (editable) addFloatElement(jElem.find('.more-operation')[0], fEHtml, {
            displayCondition: 'hover',
            relativePosition: 'left-middle',
            style: {
                zIndex: 3
            },
            script: (fE) => {
                // Adjust float element position when item
                // is first or last item
                $(parent)
                    .on('mouseenter', () => {
                        let isFirst = (jElem.prev().length == 0)
                        let isLast = (jElem.next().length == 0)
                        if (isFirst) {
                            $(fE).css({
                                top: '12px',
                                bottom: 'unset',
                                transform: 'unset'
                            })
                        }

                        else if (isLast) {
                            $(fE).css({
                                top: 'unset',
                                bottom: '12px',
                                transform: 'unset'
                            })
                        }
                    })
                    .on('mouseout', function () {
                        $(fE).css({
                            top: 'unset',
                            bottom: '50%',
                            transform: 'translateY(50%)'
                        })
                    })

                // Css float element
                $(fE).find('.op')
                    .css({
                        width: '120px',
                        padding: '12px',
                        cursor: 'pointer'
                    })
                    .on('mouseenter', function () {
                        $(this).css({
                            'background-color': '#ccc'
                        })
                    })
                    .on('mouseout', function () {
                        $(this).css({
                            'background-color': 'white'
                        })
                    })

                // Operations
                $(fE).find('.edit').click(function () {
                    fE.style.display = 'none'
                    setTimeout(() => fE.style.display = 'block', 100)

                    let popupHtml = `<form>
                        <input type="text" placeholder="@productname" class="primary" name="name">
                        <input type="text" placeholder="@price" class="primary" name="price">
                        <input type="text" placeholder="@dd/mm/yy" class="primary" name="date">
                        <span class="errors"></span>
                        <button class="submit" style="display: none;"></button>
                    </form>`

                    popUp(popupHtml, {
                        hideCloseButton: true,
                        style: {
                            'width': 'min-content'
                        },
                        script: (jPopUp) => {
                            jPopUp.find('input[name="name"]').val(itemName)
                            jPopUp.find('input[name="price"]').val(price)
                            jPopUp.find('input[name="date"]').val(transactionDate)

                            FormManager(jPopUp.find('form'), {
                                fieldNamesAndRequires: [
                                    { name: 'name', requires: 'notEmpty', vnName: 'Tên giao dịch' },
                                    { name: 'price', requires: ['notEmpty', 'isNonNegativeInt'], vnName: 'Số tiền' },
                                    {
                                        name: 'date', requires: ['notEmpty', 'isMyCustomDate', (val, errors) => {
                                            let error = checkSmallTransactionDate(val)
                                            if (!error) return true
                                            errors.push(error)
                                            return false
                                        }],
                                        vnName: 'Ngày giao dịch'
                                    }
                                ],
                                onSubmit: (formData) => {
                                    let name = formData.name
                                    let price = formData.price
                                    let date = formData.date
                                    
                                    api.updateSmallTransaction(id, name, price, date, {
                                        onDone: (newItem) => {
                                            newItem.fullname = user.fullname
                                            jPopUp.find('input').val('')
                                            jPopUp.remove()
                                            jElem.remove()
                                            appendNewItemToListItem(newItem)
                                            reloadQuickInfo()
                                        }
                                    })
                                }
                            })
                        },
                        buttonHtmls: ['Hủy thay đổi', 'Lưu thay đổi'],
                        buttonClickHandlers: [
                            (jPopUp) => jPopUp.remove(),
                            (jPopUp) => jPopUp.find('form button.submit').click()
                        ],
                        style: {
                            'background-color': '#79E0EE'
                        }
                    })
                })

                $(fE).find('.delete').click(function () {
                    api.deleteSmallTransaction(id, {
                        onDone: () => {
                            jElem.remove()
                            reloadQuickInfo()
                        }
                    })
                })
            }
        })

        return jElem
    }

    let currMonth = new Date().getMonth() + 1
    let currYear = new Date().getFullYear()

    let jTimeControl = $('#input-transaction #list-transactions .time-control')
    let jTimeDisplay = jTimeControl.find('.time')
    jTimeControl.find('.prev-month').click(() => {
        if (currMonth == 1) {
            currMonth = 12
            currYear--
        } else {
            currMonth--
        }
        loadListTransaction()
    })
    jTimeControl.find('.next-month').click(() => {
        if (currMonth == 12) {
            currMonth = 1
            currYear++
        } else {
            currMonth++
        }
        loadListTransaction()
    })

    function loadListTransaction() {
        jTimeDisplay.html(`${currMonth}/${currYear}`)
        jCont.find('#transactions').html('')
        let ofUserId = jCont.find('.user-selection').val()
        if (ofUserId == '') ofUserId = null

        api.getSmallTransaction(currentRoom.id, currMonth, currYear, ofUserId, {
            onDone: (items) => {
                items.sort((i1, i2) => {
                    return CustomDateManager.d1SmallerThanD2(i1.transactionDate, i2.transactionDate) ? 1 : -1
                })
                for (let itemElement of items.map(item => createItemInListTransaction(item))) {
                    jCont.find('#transactions').append(itemElement)
                }
            }
        })
    }
    loadListTransaction()
    reloadQuickInfo()
}

let initedEventListRoomMembersView = false
function loadListRoomMembersView() {
    $('#view-join-room-req-of-room').toggle(currentRoom.isAdmin)
    let countReqs = $('#view-join-room-req-of-room .count').hide()
    if (currentRoom.isAdmin) {
        api.getJoinRoomRequestOfRoom(currentRoom.id, {
            onDone: (joinReqs) => {
                countReqs.text(joinReqs.length)
                countReqs.toggle(joinReqs.length != 0)
            }
        })
    }
    let jCont = $('#room-members')
    jCont.find('.list-members > .item').remove()

    function createMemberAsLine({ id, fullname, avatarUrl, phoneNumber, bankNumber, bankName }) {
        let html = `
            <div class="item">
                <img src="${avatarUrl || defaultAvatarUrl}" alt="">
                <div class="name">${fullname}</div>
                <div class="phone">${phoneNumber || 'Không có'}</div>
                <div class="bank">
                    ${bankName}: ${bankNumber}
                </div>
            </div>
        `

        let jItem = $(html)
        if (currentRoom.isAdmin && id != user.id) {
            let fEHtml = '<button class="del-member primary">Xóa thành viên</button>'
            addFloatElement(jItem[0], fEHtml, {
                displayCondition: 'hover',
                script: (fE) => {
                    // Adjust float element position when item is first item
                    jItem
                    .on('mouseenter', () => {
                        let isFirst = (jItem.prev('.item').length == 0)
                        if (isFirst) {
                            $(fE).css({
                                top: '100%',
                                bottom: 'unset',
                                'z-index': 100
                            })
                        }
                    })
                    .on('onmouseleave', function () {
                        $(fE).css({
                            top: 'unset',
                            bottom: '100%',
                            'z-index': null
                        })
                    })
    
                    $(fE).find('button.del-member').click(() => {
                        $(fE).hide()
                        setTimeout(() => $(fE).show(), 100)
                        let f = () => api.removeMember({roomId: currentRoom.id, userId: id}, () => jItem.remove())
                        popUpConfirm('Bạn có chắc chắn muốn xóa thành viên này không?', f)
                    })
                }
            })
        }
        return jItem
    }

    api.getUsersOfRoomId(currentRoom.id, {
        onDone: (users) => {
            let jList = jCont.find('.list-members')
            users.map(user => createMemberAsLine(user)).forEach(j => jList.append(j))
            let jImgs = jList.find('.item > img')
            jImgs.width(jImgs.height())
        }
    })

    jCont.find('.room-info .room-name .value').html(currentRoom.roomName)
    jCont.find('.room-info .room-address .value').html(currentRoom.address)
    jCont.find('.room-info .room-id .value').html(currentRoom.id)
    jCont.find('.room-info .change-admin').toggle(currentRoom.isAdmin)

    if (!initedEventListRoomMembersView) {
        initedEventListRoomMembersView = true
        jCont.find('.room-info .change-admin').click(() => {
            api.getUsersOfRoomId(currentRoom.id, {
                onDone: (users) => {
                    let html = `
                        Chọn thành viên làm trường phòng<br>
                        <select>
                            ${users.map(({id, fullname}) => `<option value="${id}">${fullname}</option>`).join('')}
                        </select>
                    `
                    popUp(html, {
                        script: ($popUp) => {
                            $popUp.find('select').val(user.id)
                        },
                        hideCloseButton: true,
                        buttonHtmls: ['Thoát', 'Lưu lại'],
                        buttonClickHandlers: [$pu => $pu.remove(), ($pu) => {
                            let newUserId = $pu.find('select').val()
                            if (newUserId == user.id) {
                                $pu.remove()
                                return
                            }
                            popUpConfirm('Bạn có chắc chắn muốn nhượng quyền trưởng phòng không?', () => {
                                $pu.remove()
                                api.changeAdmin({
                                    roomId: currentRoom.id,
                                    newAdminId: newUserId
                                }, () => {
                                    showListRooms()
                                })
                            })
                        }]
                    })
                }
            })
        })
        jCont.find('.room-info .leave-room').click(() => {
            popUpConfirm('Bạn có chắc chắn muốn rời khỏi phòng không?', () => {
                api.leaveRoom({roomId: currentRoom.id}, () => {
                    showListRooms()
                }, (m) => {
                    if (m == ERROR.ROOM_ADMIN_CAN_NOT_LEAVE_ROOM) {
                        popUpMessage('Bạn cần phải chuyển quyền trưởng phòng cho người khác trước khi rời phòng!')
                    }
                })
            })
        })
    }
}

function loadFixedCostsView() {
    let isAdmin = currentRoom.isAdmin
    let jCont = $('#fixed-costs')
    let limit = 5

    jCont.find('#list-fixed-costs-admin').toggle(isAdmin)
    jCont.find('#list-fixed-costs').toggle(!isAdmin)

    function loadMemberView() {
        let jItemsCont = jCont.find('.left #list-fixed-costs .fixed-costs').html('')
        api.getPayFeeWDStatus(currentRoom.id, false, {
            onDone: (fees) => {
                CustomDateManager.sortByDate(fees, f => f.deadline, true)
                let total = fees.reduce((prev, {pricePerUser, status}) => prev + (status == 0 ? Math.ceil(Number(pricePerUser)) : 0), 0)
                jItemsCont.parent().find('.total .value').html(`${total}k`)
                function add(startIdx, endIdx) {
                    jItemsCont.find('.more-fwd').remove()
                    for (var i = startIdx; i <= endIdx && i < fees.length; i++) {
                        let {feeName, status, pricePerUser, price, deadline} = fees[i]
                        jItemsCont.append(`<div class="item">
                            <div class="title">${feeName} (tổng: ${price}k, hạn: ${deadline})</div>
                            <div class="value${status == 1 ? ' done' : ''}">${Math.ceil(Number(pricePerUser))}k</div>
                        </div>`)
                    }
                    if (i != fees.length) {
                        let jMore = $('<div class="more-fwd text-clickable">Hiển thị thêm</div>')
                        jMore.click(() => add(endIdx + 1, endIdx + limit))
                        jItemsCont.append(jMore)
                    }
                }
                add(0, limit - 1)
            }
        })
    }

    function loadAdminView(onlyUserInRoom = true) {
        let jSCont = jCont.find('#list-fixed-costs-admin')
        jSCont.find('.users').html('')
        jSCont.find('.total').html('')
        api.getPayFeeWDStatus(currentRoom.id, true, {
            onDone: ({feesWithDealine, payStatus, users}) => {
                CustomDateManager.sortByDate(feesWithDealine, x => x.deadline, true)
                let validUsers = users.filter(({inRoom}) => !onlyUserInRoom || inRoom)
                validUsers.sort((u1, u2) => (u2.inRoom ? 1 : 0) - (u1.inRoom ? 1 : 0))
                let mapUserIdToJLine = {}
                validUsers.forEach(({fullname, id}) => {
                    mapUserIdToJLine[id] = $('<div class="line"></div>').append(`<div class="col">${fullname}</div>`)
                    jSCont.find('.users').append(mapUserIdToJLine[id])
                })
                let hideOrShowOldMembers = $(`<div class="show-old-members text-clickable">${onlyUserInRoom ? 'Hiển thị' : 'Ẩn'} các thành viên đã rời khỏi phòng</div>`)
                jSCont.find('.users').append(hideOrShowOldMembers)
                hideOrShowOldMembers.click(() => loadAdminView(!onlyUserInRoom))
                jSCont.find('.total').html('<div class="line"><div class="col">Đã đóng</div></div>')
                function addFee(startIdx, endIdx) {
                    jSCont.find('.show-more-fees').parent().remove()
                    let fees = feesWithDealine
                    let j = startIdx
                    for (let i = startIdx; i <= endIdx && i < fees.length; i++) {
                        j++
                        let feeName = fees[i].name
                        let pricePerUser = fees[i].pricePerUser
                        let idUserDoesnotHasFee = Object.keys(mapUserIdToJLine)
                        let count_pay_done = 0
                        let count_pay_total = 0
                        payStatus.filter(({feeId}) => feeId == fees[i].id).forEach(({userId, status}) => {
                            count_pay_total += 1
                            if (status == 1) count_pay_done += 1
                            if (!mapUserIdToJLine[userId]) return
                            let doneClass = status == 1 ? ' done' : ''
                            let jE = $(`<div class="col${doneClass}">${feeName}<br>${Math.ceil(Number(pricePerUser))}k</div>`)
                                .click(() => {
                                    api.changePayFeeWDStatus(userId, fees[i].id, {
                                        onDone: () => {
                                            let oldStatusIsDone = jE.hasClass('done')
                                            jE.toggleClass('done')
                                            let oldHtml = $(jSCont.find('.total .line .col')[i + 1]).html()
                                            let newHtml = `${Number(oldHtml.split('/')[0]) + (oldStatusIsDone ? -1 : 1)}/${oldHtml.split('/')[1]}`
                                            $(jSCont.find('.total .line .col')[i + 1]).html(newHtml)               
                                        }
                                    })
                                })
                            mapUserIdToJLine[userId].append(jE)
                            idUserDoesnotHasFee = idUserDoesnotHasFee.filter(id => id != userId)
                        })
                        idUserDoesnotHasFee.forEach(id => {
                            mapUserIdToJLine[id].append(`<div class="col empty"></div>`)
                        })
                        jSCont.find('.total .line').append(`<div class="col">${count_pay_done}/${count_pay_total}<br>Tổng: ${fees[i].price}k</div>`)
                    }
                    if (j != fees.length) { // Chưa hết
                        let jShowMoreFees = $('<div class="col empty"><div class="show-more-fees text-clickable">Hiển thị thêm</div></div>')
                            .click(() => addFee(endIdx + 1, endIdx + limit))
                        jSCont.find('.users .line:first-child').append(jShowMoreFees)
                        jSCont.find('.total .line').append(`<div class="col empty"><div class="show-more-fees"></div></div>`)
                    }
                }
                addFee(0, limit - 1)
            }
        })
    }

    if (isAdmin) loadAdminView()
    else loadMemberView()
}

let addFeeFormLoaded = false
function loadRequestPaymentsView() {
    let jCont = $('#request-payments')
    let jItems = jCont.find('#list-request-payments .items').html('')
    let limit = 15

    function createItemInListFees({ name, deadline, price, id }, i) {
        let nameInHtml = name ? `${i + 1}. ${name}` : ''
        let priceInHtml = price ? price + 'k' : ''
        let itemHtml = `<div class="row">
            <div class="field"><div class="wrap">${nameInHtml}</div></div>
            <div class="field"><div class="wrap">${priceInHtml}</div></div>
            <div class="field"><div class="wrap">${deadline || ''}</div></div>
        </div>`
        let jItem = $(itemHtml)

        let editable = (nameInHtml.length != 0) && true
        if (!editable) return jItem

        let fEHtml = `<div class="fee-with-deadline-ops">
            <button class="primary edit">Sửa</button>
            <button class="primary delete">Xóa</button>
        </div>`
        addFloatElement(jItem[0], fEHtml, {
            displayCondition: 'hover',
            script: (fE) => {
                let style =  {
                    padding: '12px',
                    width: '260px',
                    display: 'flex',
                    justifyContent: 'space-around'
                }
                Object.assign(fE.querySelector('.fee-with-deadline-ops').style, style)
            
                // Adjust float element position when item is first or last item
                jItem
                .on('mouseenter', () => {
                    let isFirst = (jItem.prev().length == 0)
                    if (isFirst) {
                        $(fE).css({
                            top: '100%',
                            bottom: 'unset',
                            'z-index': 10
                        })
                    }
                })
                .on('onmouseleave', function () {
                    $(fE).css({
                        top: 'unset',
                        bottom: '100%',
                        'z-index': null
                    })
                })

                $(fE).find('button.delete').click(() => {
                    api.deleteFeeWithDeadline(id, {
                        onDone: () => loadRequestPaymentsView()
                    })
                })

                $(fE).find('button.edit').click(() => {
                    $(fE).hide()
                    setTimeout(() => $(fE).show(), 100)
                    let popupHtml = `
                        <form>
                            <input type="text" class="primary" name="name">
                            <input type="text" class="primary" name="price">
                            <input type="text" class="primary" name="deadline">
                            <span class="errors"></span>
                            <button class="submit" style="display: none;"></button>
                        </form>
                    `
                    popUp(popupHtml, {
                        hideCloseButton: true,
                        buttonHtmls: ['Thoát', 'Lưu cập nhật'],
                        buttonClickHandlers: [
                            (jPopUp) => jPopUp.remove(),
                            (jPopUp) => jPopUp.find('form button.submit').click()
                        ],
                        script: (jPopUp) => {
                            jPopUp.find('input[name="name"]').val(name)
                            jPopUp.find('input[name="price"]').val(price)
                            jPopUp.find('input[name="deadline"]').val(deadline)
                            FormManager(jPopUp.find('form'), {
                                fieldNamesAndRequires: [
                                    {name: 'name', requires: 'notEmpty', vnName: 'Tên chi phí'},
                                    {name: 'price', requires: ['notEmpty', 'isNonNegativeInt'], vnName: 'Số tiền'},
                                    {name: 'deadline', requires: ['notEmpty', 'isMyCustomDate'], vnName: 'Thời hạn'}
                                ],
                                onSubmit: (formData) => {
                                    let {name, price, deadline} = formData
                                    api.updateFeeWithDeadline(id, name, price, deadline, {
                                        onDone: (fee) => {
                                            jPopUp.remove()
                                            loadRequestPaymentsView()
                                        }
                                    })
                                }
                            })
                        }
                    })
                })
            }
        })
        return jItem
    }
    if (!addFeeFormLoaded) {
        addFeeFormLoaded = true
        FormManager(jCont.find('form'), {
            fieldNamesAndRequires: [
                {name: 'name', requires: 'notEmpty', vnName: 'Tên chi phí'},
                {name: 'price', requires: ['notEmpty', 'isNonNegativeInt'], vnName: 'Số tiền'},
                {name: 'deadline', requires: ['notEmpty', 'isMyCustomDate'], vnName: 'Thời hạn'}
            ],
            onSubmit: (formData) => {
                let {name, price, deadline} = formData
                api.createFeesWithDeadline(currentRoom.id, name, price, deadline, {
                    onDone: () => loadRequestPaymentsView()
                })
            },
            ipPlaceHolderSameVnName: false
        })
        jCont.find('.operations .add-fee-with-dealine').click(() => jCont.find('form button.submit').click())
    }

    let fees
    function displayFees(startIdx, endIdx) {
        jItems.find('.more-results').remove()
        for (var i = startIdx; i <= endIdx && i < fees.length; i++) {
            jItems.append(createItemInListFees(fees[i], i))
        }
        let remain = (i != fees.length) && (Object.keys(fees[i]).length != 0)
        if (remain) {
            jItems.append(`<div class="more-results text-clickable">Xem thêm ${limit} chi phí có thời hạn</div>`)
            jItems.find('.more-results').click(() => {
                displayFees(endIdx + 1, endIdx + limit)
            })
        }
    }
    api.getFeesWithDeadline(currentRoom.id, {
        onDone: (result) => {
            fees = result
            fees.sort((f1, f2) => {
                return CustomDateManager.d1SmallerThanD2(f1.deadline, f2.deadline) ? 1 : -1
            })
            // padding view
            while (fees.length < 6) {
                fees.push({})
            }
            displayFees(0, limit - 1)
        }
    })
}

function loadRoomStatisticView() {
    let $cont = $('#room-statistics')

    // Small transactions
    let $stCont = $cont.find('.small-trans')
    let now = {
        month: new Date().getMonth() + 1,
        year: new Date().getFullYear()
    }
    let prevMonth = {
        month: now.month == 1 ? 12 : now.month - 1,
        year: now.month == 1 ? now.year - 1 : now.year
    }
    $stCont.find('.header').html(`Thống kê về các giao dịch nhỏ tháng trước: ${prevMonth.month}/${prevMonth.year}`)
    $stCont.find('.total-prev-month .value').html('')
    $stCont.find('.room-average .value').html('')
    $stCont.find('.details').html('')
    api.roomSmallTransactionPrevMonthStatistic({roomId: currentRoom.id}, ({total, average, memberSpendings}) => {
        $stCont.find('.total-prev-month .value').html(total)
        average = Math.ceil(Number(average))
        $stCont.find('.room-average .value').html(average)
        memberSpendings.forEach(({fullname, spend}) => {
            spend = Math.ceil(Number(spend))
            let delta = average - spend
            let html = `<div class="user">
                <div class="fullname">${fullname}</div>
                <div class="spend">Đã chi: ${spend}k</div>
                <div class="delta ${delta > 0 ? 'neg' : ''}">${delta > 0 ? 'Phải trả' : 'Được nhận lại'}: ${Math.abs(delta)}k</div>
            </div>`
            $stCont.find('.details').append(html)
        })
    })
}

$('#left-side-bar .item[tab-id="input-transaction"]').click(loadInputTransactionView)
$('#left-side-bar .item[tab-id="room-members"]').click(loadListRoomMembersView)
$('#left-side-bar .item[tab-id="fixed-costs"]').click(loadFixedCostsView)
$('#left-side-bar .item[tab-id="room-statistics"]').click(loadRoomStatisticView)
$('#left-side-bar .item[tab-id="request-payments"]').click(loadRequestPaymentsView)

let user_ops_html = `<div class="ops-wrapper">
    <div class="update-avatar">Đổi ảnh đại diện</div>
    <div class="update-profile">Cập nhật thông tin</div>
    <div class="security-questions">Câu hỏi bảo mật</div>
    <div class="change-password">Đổi mật khẩu</div>
</div>`

addFloatElement($('.user-icon-wrapper')[0], user_ops_html, {
    relativePosition: 'middle-bottom',
    style: {
        padding: '8px',
        cursor: 'pointer'
    },
    displayCondition: 'hover',
    script: (fE) => {
        $fE = $(fE)
        $fE.find('.change-password').click(() => {
            $fE.hide()
            setTimeout(() => $fE.show(), 100)
            let html = `
                <div class="header">Đổi mật khẩu</div>
                <form>
                    <input type="text" class="primary" name="newPassword">
                    <input type="text" class="primary" name="newPasswordConfirm">
                    <input type="text" class="primary" name="oldPassword">
                    <span class="errors"></span>
                    <button class="submit" style="display: none;"></button>
                </form>
            `

            popUp(html, {
                hideCloseButton: true,
                script: (jPopUp) => {
                    FormManager(jPopUp.find('form'), {
                        removeInputValueIfFormValid: false,
                        fieldNamesAndRequires: [
                            { name: 'newPassword', requires: 'notEmpty', vnName: 'Mật khẩu mới' },
                            { name: 'newPasswordConfirm', requires: 'notEmpty', vnName: 'Nhập lại mật khẩu mới' },
                            { name: 'oldPassword', requires: 'notEmpty', vnName: 'Mật khẩu hiện tại' }
                        ],
                        onSubmit: (formData) => {
                            let {newPassword, newPasswordConfirm, oldPassword} = formData
                            if (newPassword != newPasswordConfirm) {
                                popUpMessage('Mật khẩu mới và nội dung nhập lại không trùng khớp!')
                                return
                            }
                            api.changePassword(formData, () => {
                                popUpMessage('Đổi mật khẩu thành công!')
                                jPopUp.remove()
                            }, (mes) => {
                                if (mes == ERROR.WRONG_PASSWORD) {
                                    popUpMessage('Sai mật khẩu cũ!')
                                }
                            })
                        }
                    })
                },
                buttonHtmls: ['Thoát', 'Đổi mật khẩu'],
                buttonClickHandlers: [
                    (jPopUp) => jPopUp.remove(),
                    (jPopUp) => jPopUp.find('form button.submit').click()
                ]
            })
        })
        $fE.find('.update-profile').click(() => {
            $fE.hide()
            setTimeout(() => $fE.show(), 100)
            let html = `
                <div class="header">Cập nhật thông tin cá nhân</div>
                <form>
                    <input type="text" class="primary" name="fullname">
                    <input type="text" class="primary" name="phoneNumber">
                    <input type="text" class="primary" name="bankName">
                    <input type="text" class="primary" name="bankNumber">
                    <span class="errors"></span>
                    <button class="submit" style="display: none;"></button>
                </form>
            `

            popUp(html, {
                hideCloseButton: true,
                script: (jPopUp) => {
                    FormManager(jPopUp.find('form'), {
                        removeInputValueIfFormValid: false,
                        fieldNamesAndRequires: [
                            { name: 'fullname', requires: 'notEmpty', vnName: 'Tên đầy đủ' },
                            { name: 'phoneNumber', vnName: 'Số điện thoại' },
                            { name: 'bankName', vnName: 'Tên ngân hàng' },
                            { name: 'bankNumber', vnName: 'Số ngân hàng' }
                        ],
                        initInputValue: user,
                        onSubmit: (formData) => {
                            api.updateProfile(formData, () => {
                                api.getUserInfo({
                                    onDone: (u) => {
                                        user = u
                                        popUpMessage('Cập nhật thông tin thành công!')
                                        jPopUp.remove()
                                    }
                                })
                            })
                        }
                    })
                },
                buttonHtmls: ['Thoát', 'Cập nhật thông tin'],
                buttonClickHandlers: [
                    (jPopUp) => jPopUp.remove(),
                    (jPopUp) => jPopUp.find('form button.submit').click()
                ]
            })
        })
        $fE.find('.security-questions').click(() => {
            $fE.hide()
            setTimeout(() => $fE.show(), 100)
            let html = `
                <div class="header">Câu hỏi bảo mật của tôi</div>
                <div class="list-security-questions"></div>
            `

            let refreshPopUp = null

            popUp(html, {
                hideCloseButton: true,
                style: {
                    width: '1000px',
                    height: '640px'
                },
                script: (jPopUp) => {
                    refreshPopUp = function refresh() {
                        jPopUp.find('.list-security-questions').html('')
                        api.getSecurityQuestions((qs) => {
                            qs.forEach(({question, id}) => {
                                let html = `<div class="item">
                                    <div class="q">${question}</div>
                                    <div class="del">Xóa</div>
                                </div>`

                                let jItem = $(html)
                                jPopUp.find('.list-security-questions').append(jItem)
                                jItem.find('.del').click(() => {
                                    popUpConfirm('Bạn có chắc chắn muốn xóa không?', () => {
                                        api.deleteSecurityQuestion({id}, () => jItem.remove())
                                    })
                                })
                            })
                        })
                    }
                    refreshPopUp()
                },
                buttonHtmls: ['Thoát', 'Thêm câu hỏi bảo mật'],
                buttonClickHandlers: [
                    (jPopUp) => jPopUp.remove(),
                    (jPopUp) => {
                        let html = `
                            <div class="header">Thêm câu hỏi bảo mật</div>
                            <form>
                                <input type="text" class="primary" name="question">
                                <input type="text" class="primary" name="answer">
                                <input type="text" class="primary" name="password">
                                <span class="errors"></span>
                                <button class="submit" style="display: none;"></button>
                            </form>
                        `

                        popUp(html, {
                            hideCloseButton: true,
                            script: (jPopUp) => {
                                FormManager(jPopUp.find('form'), {
                                    removeInputValueIfFormValid: false,
                                    fieldNamesAndRequires: [
                                        { name: 'question', requires: 'notEmpty', vnName: 'Nội dung câu hỏi' },
                                        { name: 'answer', requires: 'notEmpty', vnName: 'Câu trả lời' },
                                        { name: 'password', requires: 'notEmpty', vnName: 'Mật khẩu hiện tại' }
                                    ],
                                    onSubmit: (formData) => {
                                        api.addSecurityQuestion(formData, () => {
                                            popUpMessage('Thêm thành công!')
                                            refreshPopUp()
                                            jPopUp.remove()
                                        }, (mes) => {
                                            if (mes == ERROR.WRONG_PASSWORD) {
                                                popUpMessage('Sai mật khẩu cũ!')
                                            }
                                        })
                                    }
                                })
                            },
                            buttonHtmls: ['Thoát', 'Thêm'],
                            buttonClickHandlers: [
                                (jPopUp) => jPopUp.remove(),
                                (jPopUp) => jPopUp.find('form button.submit').click()
                            ]
                        })
                    }
                ]
            })
        })
        $fE.find('.update-avatar').click(() => {
            $fE.hide()
            setTimeout(() => $fE.show(), 100)

            let html = `<div class="avatar">
                <img src="" alt="">
                <div class="ops">
                    <input type="file" accept="image/*" hidden>
                    <button class="upload">Tải lên ảnh mới</button>
                    <button class="delete">Xóa ảnh đại diện</button>
                </div>
            </div>
            `

            let currUrl = user.avatarUrl || defaultAvatarUrl
            popUp(html, {
                hideCloseButton: true,
                script: (jPopUp) => {
                    let setImgSrc = (src) => jPopUp.find('.avatar img').attr('src', src)
                    setImgSrc(currUrl)
                    jPopUp.find('.content').addClass('user-avatar')
                    jPopUp.find('.delete').click(() => {
                        currUrl = defaultAvatarUrl
                        setImgSrc(currUrl)
                    })
                    jPopUp.find('.upload').click(() => {
                        jPopUp.find('input[type="file"]').click()
                    })
                    jPopUp.find('input[type="file"]').on('change', function () {
                        let input = this
                        if (input.files && input.files[0]) {
                            var reader = new FileReader();
                            reader.onload = function (e) {
                                currUrl = e.target.result
                                setImgSrc(currUrl)
                            };
                            reader.readAsDataURL(input.files[0]);
                        }
                    })
                },
                scriptAfterAppend: (jPopUp) => {
                    let jImg = jPopUp.find('.avatar img')
                    jImg.height(jImg.width())
                },
                buttonHtmls: ['Thoát', 'Lưu ảnh đại diện'],
                buttonClickHandlers: [
                    (jPopUp) => jPopUp.remove(),
                    (jPopUp) => {
                        api.updateAvatarUrl({avatarUrl: currUrl}, () => {
                            jPopUp.remove()
                            $('#nav-right-side img').attr('src', currUrl)
                            user.avatarUrl = currUrl
                        })
                    }
                ],
                style: {
                    width: '600px'
                }
            })
        })
    }
})

// $('#list-rooms .normal-rooms .room')[0].click()
// $('#left-side-bar .item[tab-id="room-statistics"]').click()
