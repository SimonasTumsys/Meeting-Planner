import React, { useEffect, useState } from "react";

const MeetingList = () => {
  const [meetings, setMeetings] = useState([]);
  const token = "Bearer " + localStorage.getItem("jwt");

  useEffect(() => {
    fetch("/meeting", {
      method: "GET",
      headers: { Authorization: token },
      "Content-Type": "application/json",
    })
      .then((response) => response.json())
      .then((data) => setMeetings(data))
      .catch((err) => console.error(err));
  }, []);

  return (
    <>
      <div className="custom_center_container">
        <div className="flex flex-col sm:flex-row gap-6">
          {meetings.map((meeting) => (
            <div key={meeting.id}>
              <div>{meeting.name}</div>
              <div>{meeting.description}</div>
              <div>{meeting.category}</div>
              <div>{meeting.type}</div>
              <div>{meeting.startDate}</div>
              <div>{meeting.endDate}</div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default MeetingList;
