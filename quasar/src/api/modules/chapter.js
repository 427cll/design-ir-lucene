import request from '../request';

export function listChapters(pgn) {
  return request({
    url: `/chapter?pageIndex=${pgn.pageIndex}&pageSize=${pgn.pageSize}`,
    method: "GET"
  })
}

export function getChapter(id) {
  return request({
    url: `/chapter/${id}`,
    method: "GET"
  })
}

export function saveChapter(form) {
  return request({
    method: "POST",
    url: "/chapter",
    data: form
  })
}

export function deleteChapter(id) {
  return request({
    method: "DELETE",
    url: `/chapter/${id}`
  })
}




