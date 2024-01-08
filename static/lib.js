
let l = console.log

function fn_reZoom() {
    let vr_checkpoints = [
        [800, 0.6],
        [1200, 0.7],
        [1536, 0.8],
        [1920, 1],
        [2560, 4/3],
        [3400, 1.7]
    ]
    let vr_numPoints = vr_checkpoints.length
    vr_checkpoints.sort((a, b) => a[0] - b[0])
    let vr_minCPWidth = vr_checkpoints[0][0]
    let vr_maxCPWidth = vr_checkpoints[vr_numPoints - 1][0]

    let vr_width = window.innerWidth || document.clientWidth || document.body?.clientWidth || 1536
    let vr_zoom = 1

    if (vr_width <= vr_minCPWidth) vr_zoom = vr_checkpoints[0][1]
    else if (vr_maxCPWidth < vr_width) vr_zoom = vr_checkpoints[vr_numPoints - 1][1]
    else {
        for (let i = 0; i < vr_numPoints - 1; i++) {
            if (vr_checkpoints[i][0] < vr_width) {
                vr_zoom = vr_checkpoints[i][1] + (vr_checkpoints[i + 1][1] - vr_checkpoints[i][1])*(vr_width - vr_checkpoints[i][0])/(vr_checkpoints[i + 1][0] - vr_checkpoints[i][0])
            }
        }
    }

    document.body.style.zoom = vr_zoom
    return fn_reZoom
}
$(() => window.onresize = fn_reZoom())

function addFloatElement(e, html, option = null) {
    let defaultOption = {
        script: null, // Fuction do to after insert html to float element, pass float element to first parameters
        style: null, // Additional style css for float element
        relativePosition: 'middle-top',
        definePositionProp: true,
        displayCondition: 'always'
    }
    if (option) {
        for (let key in option) {
            defaultOption[key] = option[key]
        }
    }
    option = defaultOption

    let floatElement = document.createElement('div')
    floatElement.innerHTML = html
    floatElement.classList.add('float-element')

    let mapRelativePositionToStyle = {
        'middle-top': {
            bottom: '100%',
            left: '50%',
            transform: 'translateX(-50%)'
        },
        'left-middle': {
            bottom: '50%',
            right: '100%',
            transform: 'translateY(50%)'
        },
        'middle-bottom': {
            top: '100%',
            left: '50%',
            transform: 'translateX(-50%)'
        }
    }

    if (option.definePositionProp) {
        e.style.position = 'relative'
    }

    Object.assign(floatElement.style, mapRelativePositionToStyle[option.relativePosition], option.style || {}, {
        position: 'absolute',
        backgroundColor: 'white',
        border: '1px solid #ccc',
        borderRadius: '4px',
        overflow: 'hidden',
        boxShadow: '0px 5px 20px 1px #ccc'
    })

    if (option.script) {
        option.script(floatElement)
    }

    if (option.displayCondition == 'always') {
        e.appendChild(floatElement)
    }
    else if (option.displayCondition == 'hover') {
        e.onmouseenter = () => {
            e.appendChild(floatElement)
        }
        e.onmouseleave = () => {
            e.removeChild(floatElement)
        }
    }
    else if (option.displayCondition == 'focus') {
        e.onfocus = () => {
            e.appendChild(floatElement)
        }
        e.onblur = () => {
            e.removeChild(floatElement)
        }
    }

    function removeFloatElement() {
        if (option.displayCondition == 'hover') {
            e.onmouseenter = null
            e.onmouseleave = null
        }
        floatElement.remove()
    }

    return removeFloatElement
}

function popUp(html, option) {
    let defaultOption = {
        script: null,
        scriptAfterAppend: null,
        buttonHtmls: [],
        buttonClickHandlers: [],
        hideCloseButton: false
    }
    if (option) {
        for (let key in option) {
            defaultOption[key] = option[key]
        }
    }
    option = defaultOption

    let popupHtml = `<div class="pop-up-wrap">
        <div class="pop-up">
            <div class="content">${html}</div>
            <div class="button-group">
                ${option.buttonHtmls.map((text, i) => `<button class="adtn-button-${i} primary">${text}</button>`).join('')}
                <button class="close primary">Đóng</button>
            </div>
        </div>
    </div>`

    let jPopUp = $(popupHtml)
    jPopUp.click(() => jPopUp.remove())
    jPopUp.children().click(e => e.stopPropagation())
    jPopUp.find('button.close').click(() => jPopUp.remove())

    if (option.script) {
        option.script(jPopUp)
    }
    option.buttonClickHandlers.forEach((hdl, i) => {
        jPopUp.find('button.adtn-button-' + i).click(() => hdl(jPopUp))
    })
    if (option.hideCloseButton) {
        jPopUp.find('button.close').hide()
    }
    if (option.style) {
        jPopUp.children().css(option.style)
    }

    $(document.body).append(jPopUp)

    if (option.scriptAfterAppend) {
        option.scriptAfterAppend(jPopUp)
    }
}

function popUpMessage(mes) {
    popUp(mes, {
        style: {
            width: '400px',
            height: '300px',
            'font-size': '20px'
        }
    })
}

function popUpConfirm(html, f) {
    popUp(html, {
        hideCloseButton: true,
        buttonHtmls: ['Thoát', 'Xác nhận'],
        buttonClickHandlers: [$pu => $pu.remove(), ($pu) => {
            $pu.remove()
            f()
        }],
        style: {
            width: '400px',
            height: '300px',
            'font-size': '20px'
        }
    })
}

let CustomDateManager = (() => {
    let a = {
        d1SmallerThanD2: (date1, date2) => {
            return a.toCompareableNumber(date1) - a.toCompareableNumber(date2) < 0
        },
        toCustomDate: (date) => {
            let d = date.getDate()
            let m = date.getMonth() + 1
            let y = date.getFullYear()
            return `${d}/${m}/${y}`
        },
        toCompareableNumber: (cDate) => {
            let [d1, m1, y1] = cDate.split('/').map(i => Number(i))
            return d1 + m1*100 + y1*10000
        },
        sortByDate: (arr, get, desc = false) => {
            arr.sort((x, y) => {
                return (a.toCompareableNumber(get(x)) - a.toCompareableNumber(get(y)))*(desc ? -1 : 1)
            })
        },
        now: () => a.toCustomDate(new Date()),
        toDBFormat: (s) => {
            let [d, m, y] = s.split('/').map(i => i.length == 1 ? '0' + i : i)
            return `${y}-${m}-${d}T00:00:00.000+00:00`
        },
        fromDBFormat: (s) => {
            let [y, m, d] = s.split('T')[0].split('-').map(i => Number(i))
            return `${d}/${m}/${y}`
        }
    }
    return a
})()

let TypeManager = (() => {
    let a = {
        isEmpty: (s) => !s || s == '',
        isNumeric: (s) => !isNaN(s) && !isNaN(parseFloat(s)),
        isPositiveInterger: (s, allow0 = true) => {
            if (!a.isNumeric(s)) return false
            let n = parseFloat(s)
            return (n == Math.round(n)) && (n > 0 || (n == 0 && allow0))
        },
        isMyCustomDate: (s) => {
            if (!s || !s.split) return false
            let abc = s.split('/')
            if (!abc.length == 3) return false
            if (abc.some(n => !a.isPositiveInterger(n, false))) return false
            let [d, m, y] = abc.map(n => Number(n))
            if (d <= 0 || d >= 32) return false
            if (m <= 0 || m >= 13) return false
            if (d >= 29) {
                // TH1: Có hơn 29 ngày, và là tháng 2
                if (m == 2) {
                    // Tháng 2 có hươn 29 ngày chắc chắn sai
                    if (d >= 30) return false
                    // Tháng 2 này có 29 ngày, mà không phải năm nhuận thì sai
                    if (!(y % 4 == 0 && y % 100 != 0)) return false
                }
                // TH2: Tháng này chắc chắn khác tháng 2 có 30, hoặc 31 ngày
                else {
                    if (d == 31) {
                        if (![1, 3, 5, 7, 8, 10, 12].includes(m)) {
                            return false
                        }
                    }
                }
            }
            return true
        }
    }
    return a
})()

function FormManager(jForm, option) {
    // Require types: notEmpty, isNonNegativeInt, isMyCustomDate
    let defaultOption = {
        fieldNamesAndRequires: [], // {name: 'field name', requires: 'not empty' || ['not empty', ..., (val, errors) => {}]}
        onSubmit: () => {},
        ipPlaceHolderSameVnName: true,
        removeInputValueIfFormValid: true,
        initInputValue: null
    }
    if (option) {
        for (let key in option) {
            defaultOption[key] = option[key]
        }
    }
    option = defaultOption

    if (option.ipPlaceHolderSameVnName) {
        option.fieldNamesAndRequires.forEach(({name, vnName}) => {
            jForm.find(`input[name="${name}"]`).attr('placeholder', vnName)
        })
    }

    if (option.initInputValue) {
        for (let name in option.initInputValue) {
            jForm.find(`input[name="${name}"]`).val(option.initInputValue[name])
        }
    }

    jForm.find('button.submit').click((e) => {
        e.preventDefault()
        jForm.find('.errors').html('')
        let formData = {}
        let errors = []
        jForm.find('input').each((_, ip) => formData[$(ip).attr('name')] = $(ip).val())
        for (let { name, requires, vnName } of option.fieldNamesAndRequires) {
            if (!requires) requires = ''
            if (typeof requires == 'string') requires = requires == '' ? [] : [requires]
            let val = formData[name]
            let valValid = true
            for (let req of requires) {
                if (!valValid) break
                switch (req) {
                    case 'notEmpty':
                        if (TypeManager.isEmpty(val)) {
                            errors.push(`Không được để trống trường thông tin "${vnName || name}"`)
                            valValid = false
                        }
                        break;
                    case 'isNonNegativeInt':
                        if (!TypeManager.isPositiveInterger(val)) {
                            errors.push(`Giá trị của trường thông tin "${vnName || name}" phải là số nguyên không âm`)
                            valValid = false
                        } else {
                            formData[name] = String(Math.round(Number(val)))
                        }
                        break;
                    case 'isMyCustomDate':
                        if (!TypeManager.isMyCustomDate(val)) {
                            errors.push(`Giá trị của trường thông tin "${vnName || name}" phải là thời gian hợp lệ có dạng dd/mm/yyyy`)
                            valValid = false
                        }
                        break;
                }
                if (typeof req == 'function') {
                    valValid = req(val, errors)
                }
            }
        }

        if (errors.length == 0) {
            if (option.removeInputValueIfFormValid) jForm.find('input').val('')
            jForm.find('.errors').html('')
            option.onSubmit(formData)
        }

        else {
            jForm.find('.errors').html(
                errors.map(err => `* <span class="errors">${err}</span>`).join('<br>')
            )
        }
    })
}

function resizeImg(jImgs) {
    let h = jImgs.height()
    if (h != 0) jImgs.width(h)
    else setTimeout(() => resizeImg(jImgs), 1)
}

function $Chart(cats, values, { xlabel, ylabel, valTrans, maxHeight = 0.8 } = {}) {
    let $chart = $('<div class="chart"></div>')

    if (ylabel) {
        let $left = $('<div class="left"></div>')
        $chart.append($left)
        $left.append($(`<div class="ylabel">${ylabel}</div>`))
    }

    let $right = $('<div class="right"></div>')
    $chart.append($right)

    let $mainChart = $('<div class="main-chart"></div>')
    $right.append($mainChart)
    let maxValue = values.reduce((max, v) => Math.max(max, v), -10e10)
    values.forEach(value => {
        let $col = $('<div class="col"></div>')
        $col.append(`<div class="value-explain">${valTrans ? valTrans(value) : value}</div>`)
        $col.append($(`<div class="val-visualize"></div>`).css('height', (maxHeight*value*100/maxValue) + '%'))
        $mainChart.append($col)
    })

    let $columnExplain = $('<div class="column-explain"></div>').html(
        cats.map(cat => `<div class="col">${cat}</div>`).join('')
    )
    $right.append($columnExplain)

    if (xlabel) {
        $right.append($(`<div class="xlabel">${xlabel}</div>`))
    }

    return $chart
}

function OCPHS(s, f, hasCondition = false) {
    let r = {}
    for (let i of s) {
        let [k, v, c] = f(i)
        if (!hasCondition || c) r[k] = v
    }
    return r
}

function ACPHS(s, f, hasCondition = false) {
    let r = []
    for (let i of s) {
        let [v, c] = f(i)
        if (!hasCondition || c) r.push(v)
    }
    return r
}

function range(a, b = null) {
    let s = b ? a : 0
    let e = b ? b : a
    let r = []
    for (let i = s; i < e; i++) r.push(i)
    return r
}
