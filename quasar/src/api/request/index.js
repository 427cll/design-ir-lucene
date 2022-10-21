import axios from 'axios';
import { BASE_URL, TIMEOUT } from './config'
const service = axios.create({
    baseURL: BASE_URL,
    timeout: TIMEOUT
});

service.interceptors.request.use(
    config => {
        /* let user = localStorage.getItem("user")
        if (user) {
            let realUser = JSON.parse(user)
            config.headers['token'] = realUser.token
        } */

        return config;
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    response => {
        if (response.status === 200) {
            const res = response.data;
            if (res.code == 200) {
                return res;
            } else {
                alert(res.msg)
                return Promise.reject();
            }
        } else {
            Promise.reject();
        }
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

export default service;
