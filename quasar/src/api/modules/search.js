import request from '../request';

export function listResults(query) {
  return request({
    url: `/search?question=${query.question}&pageIndex=${query.pageIndex}&pageSize=${query.pageSize}`,
    method: "GET"
  })
}








