import { useEffect, useState } from "react";
import useMeetingFilter from "./useMeetingFilter";

function useFetch(url) {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [filterParams, setFilterParams] = useState("");
  const { ownershipFilter } = useMeetingFilter(data);

  function getFetchUrl(filterParams){

  }

  useEffect(() => {
    setLoading(true);
    fetch(getFetchUrl(), {
      method: "GET",
      headers: { Authorization: token },
      "Content-Type": "application/json",
    })
      .then((response) => response.json())
      .then((requestData) => setData(requestData))
      .catch((err) => setError(err));
  }, [filterParams]);

  return {data, loading, error};
}

export default useFetch;
