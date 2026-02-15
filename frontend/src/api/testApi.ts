import api from "./apiInstance.ts";
import {notifyError} from "../utils/toast/notifier.tsx";

export const fetchTest = async (): Promise<string> => {
  try {
    const response = await api.get<string>("/hello");
    return response.data;
  } catch (error) {
    notifyError("Error while fetching", error)
    return "";
  }
}
