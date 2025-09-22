import Stomp from 'stompjs'
import { stompWebSocket } from './setting'
import { LocalStorageService } from './storage'

class Api {
  createWSocket(path, host, port) {
    return new WSocket(path, host, port)
  }
  createStompWebSocket(path, host, port) {
    return new StompWebSocket(path, host, port)
  }
}

/**
 * Represents a WSocket.
 * @constructor
 * @param {string} path - 主体路径
 * @param {string} host - 主机名
 * @param {string} port - 端口
 */
class WSocket {
  constructor(path, host, port) {
    this.ws = null
    this.path = path
    this.host = host
    this.port = port
    this.open = () => console.log('WebSockt open')
    this.message = () => console.log('WebSockt message')
    this.error = () => console.log('WebSockt error')
    this.close = () => console.log('WebSockt close')
  }

  init() {
    this.createWebSocket()
    this.addEvent()
    return this.ws
  }

  getUrl() {
    // return `ws://${this.host}:${this.port}/${this.path}`
    // ws://192.168.2.152:4105
    if (import.meta.env.DEV) {
      return `${import.meta.env.VITE_WEBSOCKET_URL}/custom/sequenceControl`
    }
    if (import.meta.env.PROD) {
      return 'ws://' + window.location.host + `/websocket/custom/sequenceControl`
    }
    // return import.meta.env `ws://192.168.2.132:908/websocket/${this.path}`
  }

  getWS() {
    return this.ws
  }

  createWebSocket() {
    this.ws = new WebSocket(this.getUrl())
  }

  setOpen(fn) {
    this.open = fn
  }

  setMessage(fn) {
    this.message = fn
  }

  setError(fn) {
    this.error = fn
  }

  setClose(fn) {
    this.close = fn
  }

  addEvent() {
    const ws = this.ws
    ws.onopen = this.open
    ws.onmessage = this.message
    ws.onerror = this.error
    ws.onclose = this.close
  }
}

class StompWebSocket extends WSocket {
  constructor(path, host, port) {
    super(path, host, port)
    this.brokersHost = null
  }

  createWebSocket() {
    this.ws = Stomp.client(this.getUrl())
    this.setDebug()
  }

  setMessage(fn) {
    this.message = fn
  }
  setError(fn) {
    this.error = fn
  }
  setBrokersHost(val) {
    this.brokersHost = val
  }
  setDebug(val = null) {
    this.ws.debug = val
  }
  addEvent() {
    this.ws.connect(
      stompWebSocket.login,
      stompWebSocket.passcode,
      this.message,
      this.error,
      this.brokersHost
    )
  }
}

export function getWebSocket(path, type = 'WSocket') {
  let host, port, index
  let flag = true
  if (import.meta.env.MODE === 'development') {
    flag = true //true,false
    if (path === 'ws') {
      host = '192.168.1.10'
      port = '25674'
    } else {
      host = location.hostname
      port = location.port + import.meta.env.VUE_APP_BASE_API
      // host = '192.168.2.132'
      // port='908'
    }
  } else {
    host = location.hostname
    port = location.port
  }
  // 链接 后端开发 服务调试的时候使用 false，线上服务的时候使用 true
  path = flag ? `websocket/${path}` : path
  
  return new Api()[`create${type}`](path, host, port)
}
