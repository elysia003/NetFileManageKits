import axios from 'axios';
import {MANAGE_BASE_URL} from "@/components/RES_BASE_URL";
export const search = (param) => axios({
    url: `${MANAGE_BASE_URL}/search`,
    method: 'Get',
    params: param
});
export const edittitle = (data) => axios({
    url: `${MANAGE_BASE_URL}/updateTitle`,
    method: 'Get',
    params: data
});
export const regen = (data) => axios({
    url: `${MANAGE_BASE_URL}/regen`,
    method: 'Get',
    params: data
});
export const addtag = (data) => axios({
    url: `${MANAGE_BASE_URL}/addTag`,
    method: 'Get',
    params: data
});
export const getById = (data) => axios({
    url: `${MANAGE_BASE_URL}/getById`,
    method: 'Get',
    params: data
});
export const deletetag = (data) => axios({
    url: `${MANAGE_BASE_URL}/deleteTag`,
    method: 'Get',
    params: data
});
export const scan = (data) => axios({
    url: `${MANAGE_BASE_URL}/scan`,
    method: 'Get',
    params: data
});
export const scan2 = () => axios({
    url: `${MANAGE_BASE_URL}/scan2`,
    method: 'Get',
});
export const newVedio = (data) => axios({
    url: `${MANAGE_BASE_URL}/new`,
    method: 'Post',
    data: data,
    timeout: 1000 * 60 * 10
});
export const gens = (data) => axios({
    url: `${MANAGE_BASE_URL}/gens`,
    method: 'Post',
    data: data,
    timeout: 1000 * 60 * 10
});
export const getPerfix = () => axios({
    url: `${MANAGE_BASE_URL}/getPerfix`,
    method: 'Get',
});
