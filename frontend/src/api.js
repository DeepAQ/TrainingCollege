import axios from 'axios'

const base = 'http://localhost:8080/';

export default function (path, data) {
    return new Promise((resolve, reject) => {
        axios.post(path, data, {
            baseURL: base,
            headers: {
                'X-Auth-Token': localStorage.token
            },
            timeout: 3000,
            responseType: 'json'
        }).then(resp => {
            if (resp.status == 200) {
                if (resp.data.success === true) {
                    resolve(resp.data.object)
                } else {
                    reject(resp.data.msg)
                }
            } else {
                reject(`服务器错误: ${resp.status}`)
            }
        }).catch(reason => reject(reason))
    })
}