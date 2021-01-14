const UKR_CHARS = [
    "й", "ц", "у", "к", "е", "н", "г", "ш", "щ", "з", "х", "ї",
    "ф", "і", "в", "а", "п", "р", "о", "л", "д", "ж", "є",
    "я", "ч", "с", "м", "и", "т", "ь", "б", "ю", ".",

    "\'", "Х", "Ї", "Ж", "Є", "Б", "Ю", ",", "₴", "/", "\"", "№", ";", ":", "?"];

const ENG_CHARS = [
    "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "[", "]",
    "a", "s", "d", "f", "g", "h", "j", "k", "l", ";", "\'",
    "z", "x", "c", "v", "b", "n", "m", ",", ".", "/",

    "`", "{", "}", ":", "\"", "<", ">", "?", "~", "|", "@", "#", "$", "^", "&"];

let engUkrMap = new Map();
let ukrEngMap = new Map();

for (let i = 0; i < ENG_CHARS.length; i++) {
    engUkrMap.set(ENG_CHARS[i], UKR_CHARS[i]);
    ukrEngMap.set(UKR_CHARS[i], ENG_CHARS[i]);
}

function replaceChars(str, replacementMap) {
    let result = "";
    for (let character of str) {
        let replacement = replacementMap.get(character);
        if (replacement != undefined) {
            result += replacement;
        } else {
            replacement = replacementMap.get(character.toLowerCase());
            if (replacement != undefined) {
                result += replacement.toUpperCase();
            } else {
                result += character;
            }
        }
    }
    return result;
}

// https://hackernoon.com/copying-text-to-clipboard-with-javascript-df4d4988697f
function copyToClipboard(str) {
    const el = document.createElement('textarea');
    el.value = str;
    el.setAttribute('readonly', '');
    el.style.position = 'absolute';
    el.style.left = '-9999px';
    document.body.appendChild(el);
    el.select();
    document.execCommand('copy');
    document.body.removeChild(el);
}

// https://www.w3schools.com/howto/howto_js_snackbar.asp
function showSnackbar(text) {
    var snackbar = document.getElementById("snackbar");
    snackbar.innerHTML = text;
    snackbar.className = "show";
    setTimeout(function() {
        snackbar.className = snackbar.className.replace("show", "");
        snackbar.innerHTML = '';
    }, 3000);
}

document.getElementById("eng-to-ukr").onclick = () => {
    let result = replaceChars(
        document.getElementById("eng-text").value,
        engUkrMap
    );
    document.getElementById("ukr-text").value = result;
    copyToClipboard(result);
    showSnackbar('Скопійовано в буфер обміну');
}

document.getElementById("ukr-to-eng").onclick = () => {
    let result = replaceChars(
        document.getElementById("ukr-text").value,
        ukrEngMap
    );
    document.getElementById("eng-text").value = result;
    copyToClipboard(result);
    showSnackbar('Скопійовано в буфер обміну');
}