import { useEffect, useState } from "react";

function useMeetingFilter(meetings) {
  //Defining filter states and their toggle functions
  const [filterModal, setFilterModal] = useState(false);
  const [ownershipFilter, setOwnershipFilter] = useState("all");
  const [description, setDescription] = useState("");
  const [responsiblePeople, setResponsiblePeople] = useState(new Set());
  const [responsiblePerson, setResponsiblePerson] = useState(null);
  const [category, setCategory] = useState(null);
  const [type, setType] = useState(null);
  const [dateFrom, setDateFrom] = useState(null);
  const [dateTo, setDateTo] = useState(null);
  const [attendeeCount, setAttendeeCount] = useState([0, 40]);
  const [meetingFilterUrl, setMeetingFilterUrl] = useState("/meeting");

  const toggleFilterModal = () => {
    setFilterModal(!filterModal);
  };

  const meetingOwnershipFilterToggle = (event) => {
    setOwnershipFilter(event.target.value);
  };

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);
  };

  const responsiblePersonFilterToggle = (event) => {
    let rP = responsiblePeople.find((r) => r.id == event.target.value);
    setResponsiblePerson(rP);
  };

  const categoryFilterToggle = (event) => {
    setCategory(event.target.value);
  };

  const typeFilterToggle = (event) => {
    setType(event.target.value);
  };

  const dateFromFilterToggle = (event) => {
    setDateFrom(event.target.value);
  };

  const dateToFilterToggle = (event) => {
    setDateTo(event.target.value);
  };

  const handleCountFilterChange = (event, newValue) => {
    setAttendeeCount(newValue);
  };

  function getResponsiblePeople() {
    let respPeople = [];
    meetings.map((meeting) => {
      respPeople.push(meeting.responsiblePerson);
    });
    let uniqueRespPeople = Array.from(
      new Set(respPeople.map(JSON.stringify))
    ).map(JSON.parse);
    setResponsiblePeople(uniqueRespPeople);
  }

  //Creating fetch URL based on filter states, to be used with useFetch hook later on
  //Current states as dependencies
  function questionOrAnd(url) {
    return url.includes("?") ? "&" : "?";
  }

  function getOwnershipFetchUrl(url) {
    switch (ownershipFilter) {
      case "responsible":
        url += "?usersResponsibleOnly=true";
        break;
      case "attending":
        url += "?usersAttendingOnly=true";
        break;
      case "myMeetings":
        url += "?usersOnly=true";
        break;
      default:
        return url;
    }
    return url;
  }

  function getDescriptionFetchUrl(url) {
    return description ? questionOrAnd(url) + "description=" + description : "";
  }

  function getResponsiblePersonFetchUrl(url) {
    return responsiblePerson
      ? questionOrAnd(url) + "resId=" + responsiblePerson.id
      : "";
  }

  function getCategoryFetchUrl(url) {
    return category ? questionOrAnd(url) + "category=" + category : "";
  }

  function getTypeFetchUrl(url) {
    return type ? questionOrAnd(url) + "type=" + type : "";
  }

  function getAttendeeCountFromFetchUrl(url) {
    return attendeeCount[0] != 0
      ? questionOrAnd(url) + "countFrom=" + attendeeCount[0]
      : "";
  }

  function getAttendeeCountToFetchUrl(url) {
    return attendeeCount[1] != 40
      ? questionOrAnd(url) + "countTo=" + attendeeCount[1]
      : "";
  }

  function resetMeetingFilter() {
    setOwnershipFilter("all");
    setDescription("");
    setResponsiblePerson(null);
    setCategory(null);
    setType(null);
    setDateFrom(null);
    setDateTo(null);
    setAttendeeCount([0, 40]);
    setMeetingFilterUrl("/meeting");
  }

  useEffect(() => {
    let fetchUrl = "/meeting";
    fetchUrl = getOwnershipFetchUrl(fetchUrl);
    fetchUrl += getDescriptionFetchUrl(fetchUrl);
    fetchUrl += getResponsiblePersonFetchUrl(fetchUrl);
    fetchUrl += getCategoryFetchUrl(fetchUrl);
    fetchUrl += getTypeFetchUrl(fetchUrl);
    fetchUrl += getAttendeeCountFromFetchUrl(fetchUrl);
    fetchUrl += getAttendeeCountToFetchUrl(fetchUrl);
    if (fetchUrl.endsWith("?")) {
      fetchUrl = fetchUrl.replace("?", "");
    }
    setMeetingFilterUrl(fetchUrl);
  }, [
    ownershipFilter,
    description,
    responsiblePerson,
    category,
    type,
    attendeeCount,
  ]);

  return {
    filterModal,
    toggleFilterModal,
    setFilterModal,
    ownershipFilter,
    meetingOwnershipFilterToggle,
    description,
    handleDescriptionChange,
    setDescription,
    responsiblePerson,
    responsiblePersonFilterToggle,
    setResponsiblePerson,
    responsiblePeople,
    getResponsiblePeople,
    category,
    categoryFilterToggle,
    setCategory,
    type,
    typeFilterToggle,
    setType,
    dateFrom,
    dateFromFilterToggle,
    setDateFrom,
    dateTo,
    dateToFilterToggle,
    setDateTo,
    attendeeCount,
    handleCountFilterChange,
    setAttendeeCount,
    meetingFilterUrl,
    resetMeetingFilter,
  };
}

export default useMeetingFilter;
