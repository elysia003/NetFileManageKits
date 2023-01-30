import {MANAGE_BASE_URL} from "@/components/RES_BASE_URL";
import axios from 'axios';

export const getFileList = (data) => axios({
    url: `${MANAGE_BASE_URL}/getFileList`,
    method: 'Get',
    params: data
});
