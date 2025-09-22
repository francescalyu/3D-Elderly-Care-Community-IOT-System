/**
 * common define
 * @author emiila
 * 
 * Download according to the background interface file stream
 * @param {*} data
 * @param {*} filename
 * @param {*} mime
 * @param {*} bom
 */
export function downloadByData(
  data: BlobPart,
  filename: string,
  mime?: string,
  bom?: BlobPart,
) {
  const blobData = typeof bom !== 'undefined' ? [bom, data] : [data]
  const blob = new Blob(blobData, { type: mime || 'application/octet-stream' })

  const blobURL = window.URL.createObjectURL(blob)
  const tempLink = document.createElement('a')
  tempLink.style.display = 'none'
  tempLink.href = blobURL
  tempLink.setAttribute('download', filename)
  if (typeof tempLink.download === 'undefined')
    tempLink.setAttribute('target', '_blank')

  document.body.appendChild(tempLink)
  tempLink.click()
  document.body.removeChild(tempLink)
  window.URL.revokeObjectURL(blobURL)
}

export function downloadByUrl(
  url: string,
  fileName?: string,
  target?: '_self' | '_blank',
) {
  // 是否同源
  const isSameHost = new URL(url).host === location.host
  const _fileName
    = fileName || url.substring(url.lastIndexOf('/') + 1, url.length)

  if (isSameHost) {
    const link = document.createElement('a')
    link.href = url
    link.target = target || '_blank'
    if (link.download !== undefined)
      link.download = _fileName

    if (document.createEvent) {
      const e = document.createEvent('MouseEvents')
      e.initEvent('click', true, true)
      link.dispatchEvent(e)
      return
    }
    if (!url.includes('?'))
      url += '?download'

    window.open(url, target)
  }
  else {
    getBlob(url, (blob) => {
      saveAs(blob, _fileName)
    })
  }
}

function getBlob(url, cb) {
  const xhr = new XMLHttpRequest()
  xhr.open('GET', url, true)
  xhr.responseType = 'blob'
  xhr.onload = function () {
    if (xhr.status === 200)
      cb(xhr.response)
  }
  xhr.send()
}

function saveAs(blob, filename) {
  const link = document.createElement('a')
  link.href = window.URL.createObjectURL(blob)
  link.download = filename
  link.click()
  window.URL.revokeObjectURL(link.href)
}
