import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const AnonymousTokenKey = 'Anonymous-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getAnonymousToken() {
    return sessionStorage.getItem(AnonymousTokenKey)
}

export function setAnonymousToken(token) {
    return sessionStorage.setItem(AnonymousTokenKey, token)
}

export function removeAnonymousToken() {
    return sessionStorage.removeItem(AnonymousTokenKey)
}
