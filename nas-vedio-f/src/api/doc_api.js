import axios from 'axios';
import {DOC_API} from "@/components/RES_BASE_URL";
export const find = (param) => axios({
    url: `${DOC_API}/search`,
    method: 'Get',
    params: param
});
export const getById = (param) => axios({
    url: `${DOC_API}/getById`,
    method: 'Get',
    params: param
});
