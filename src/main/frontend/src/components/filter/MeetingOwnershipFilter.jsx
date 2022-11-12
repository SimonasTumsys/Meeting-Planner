import React, { useState, useEffect } from "react";
import classNames from "../../util/classNames";

const MeetingOwnershipFilter = (props) => {
  return (
    <>
      <div className="flex items-center justify-center">
        <div
          className="h-8 inline-flex shadow-md hover:shadow-lg focus:shadow-lg"
          role="group"
        >
          <button
            type="button"
            value="all"
            name="filter"
            className={classNames(
              props.filter === "all" ? "bg-green-800" : "",
              "rounded-l inline-block px-6 bg-green-600 text-white font-medium text-xs leading-tight uppercase hover:bg-green-700 focus:bg-green-700 focus:outline-none focus:ring-0 active:bg-green-800 transition duration-150 ease-in-out"
            )}
            onClick={props.toggle}
          >
            All
          </button>
          <button
            type="button"
            value="responsible"
            name="filter"
            className={classNames(
              props.filter === "responsible" ? "bg-green-800" : "",
              "inline-block px-6 bg-green-600 text-white font-medium text-xs leading-tight uppercase hover:bg-green-700 focus:bg-green-700 focus:outline-none focus:ring-0 active:bg-green-800 transition duration-150 ease-in-out"
            )}
            onClick={props.toggle}
          >
            Responsible
          </button>
          <button
            type="button"
            value="attending"
            name="filter"
            className={classNames(
              props.filter === "attending" ? "bg-green-800" : "",
              "inline-block px-6 bg-green-600 text-white font-medium text-xs leading-tight uppercase hover:bg-green-700 focus:bg-green-700 focus:outline-none focus:ring-0 active:bg-green-800 transition duration-150 ease-in-out"
            )}
            onClick={props.toggle}
          >
            Attending
          </button>
          <button
            type="button"
            value="myMeetings"
            name="filter"
            className={classNames(
              props.filter === "myMeetings" ? "bg-green-800" : "",
              "rounded-r inline-block px-6 bg-green-600 text-white font-medium text-xs leading-tight uppercase hover:bg-green-700 focus:bg-green-700 focus:outline-none focus:ring-0 active:bg-green-800 transition duration-150 ease-in-out"
            )}
            onClick={props.toggle}
          >
            My meetings
          </button>
        </div>
      </div>
    </>
  );
};

export default MeetingOwnershipFilter;
