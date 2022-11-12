import { Menu, Transition } from "@headlessui/react";
import { DotsVerticalIcon } from "@heroicons/react/outline";
import {
  ChevronLeftIcon,
  ChevronRightIcon,
  FilterIcon,
} from "@heroicons/react/solid";
import {
  add,
  eachDayOfInterval,
  endOfMonth,
  format,
  getDay,
  isEqual,
  isSameDay,
  isSameMonth,
  isToday,
  parse,
  parseISO,
  startOfToday,
} from "date-fns";
import { Fragment, useState, useEffect } from "react";
import MeetingOwnershipFilter from "../components/filter/MeetingOwnershipFilter";
import classNames from "../util/classNames";
import FilterModal from "../components/filter/FilterModal";
import { useMediaQuery } from "react-responsive";
import MeetingOwnershipDropup from "../components/filter/MeetingOwnershipDropup";
import useMeetingFilter from "../hooks/useMeetingFilter";

export default function MeetingCalendar() {
  const isMobile = useMediaQuery({ query: `(max-width: 768px)` });

  let today = startOfToday();
  let [selectedDay, setSelectedDay] = useState(today);
  let [currentMonth, setCurrentMonth] = useState(format(today, "MMM-yyyy"));
  let firstDayCurrentMonth = parse(currentMonth, "MMM-yyyy", new Date());

  const [meetings, setMeetings] = useState([]);
  const token = "Bearer " + localStorage.getItem("jwt");
  let fetchUrl = "/meeting";

  const {
    filterModal,
    toggleFilterModal,
    ownershipFilter,
    meetingOwnershipFilterToggle,
    description,
    handleDescriptionChange,
    responsiblePerson,
    responsiblePersonFilterToggle,
    responsiblePeople,
    getResponsiblePeople,
    category,
    categoryFilterToggle,
    type,
    typeFilterToggle,
    dateFrom,
    dateFromFilterToggle,
    dateTo,
    dateToFilterToggle,
    attendeeCount,
    handleCountFilterChange,
    meetingFilterUrl,
    resetMeetingFilter,
  } = useMeetingFilter(meetings);

  function getOwnershipFetchUrl() {
    switch (ownershipFilter) {
      case "responsible":
        fetchUrl += "?usersResponsibleOnly=true";
        break;
      case "attending":
        fetchUrl += "?usersAttendingOnly=true";
        break;
      case "myMeetings":
        fetchUrl += "?usersOnly=true";
        break;
      default:
        return fetchUrl;
    }
    return fetchUrl;
  }

  useEffect(() => {
    fetch(getOwnershipFetchUrl(), {
      method: "GET",
      headers: { Authorization: token },
      "Content-Type": "application/json",
    })
      .then((response) => response.json())
      .then((data) => setMeetings(data))
      .catch((err) => console.error(err));
  }, [ownershipFilter]);

  useEffect(() => {
    getResponsiblePeople();
  }, [meetings]);

  let days = eachDayOfInterval({
    start: firstDayCurrentMonth,
    end: endOfMonth(firstDayCurrentMonth),
  });

  function previousMonth() {
    let firstDayNextMonth = add(firstDayCurrentMonth, { months: -1 });
    setCurrentMonth(format(firstDayNextMonth, "MMM-yyyy"));
  }

  function nextMonth() {
    let firstDayNextMonth = add(firstDayCurrentMonth, { months: 1 });
    setCurrentMonth(format(firstDayNextMonth, "MMM-yyyy"));
  }

  let selectedDayMeetings = meetings.filter((meeting) =>
    isSameDay(parseISO(meeting.startDate), selectedDay)
  );

  return (
    <>
      {filterModal && (
        <FilterModal
          toggleFilterModal={toggleFilterModal}
          filterModal={filterModal}
          description={description}
          handleDescriptionChange={handleDescriptionChange}
          responsiblePeople={responsiblePeople}
          responsiblePerson={responsiblePerson}
          responsiblePersonFilterToggle={responsiblePersonFilterToggle}
          category={category}
          categoryFilterToggle={categoryFilterToggle}
          type={type}
          typeFilterToggle={typeFilterToggle}
          dateFrom={dateFrom}
          dateFromFilterToggle={dateFromFilterToggle}
          dateTo={dateTo}
          dateToFilterToggle={dateToFilterToggle}
          attendeeCount={attendeeCount}
          handleCountFilterChange={handleCountFilterChange}
          meetingFilterUrl={meetingFilterUrl}
          resetMeetingFilter={resetMeetingFilter}
        />
      )}
      <div className="pt-48">
        <div className="max-w-md shadow-lg px-4 mx-auto sm:px-7 md:max-w-4xl md:px-6">
          <div className="md:grid md:grid-cols-2 md:divide-x md:divide-gray-200">
            <div className="md:pr-14">
              <div className="flex items-center">
                <h2 className="flex-auto font-semibold text-gray-900">
                  {format(firstDayCurrentMonth, "MMMM yyyy")}
                </h2>
                <button
                  type="button"
                  onClick={previousMonth}
                  className="-my-1.5 flex flex-none items-center justify-center p-1.5 text-gray-400 hover:text-gray-500"
                >
                  <span className="sr-only">Previous month</span>
                  <ChevronLeftIcon className="w-5 h-5" aria-hidden="true" />
                </button>
                <button
                  onClick={nextMonth}
                  type="button"
                  className="-my-1.5 -mr-1.5 ml-2 flex flex-none items-center justify-center p-1.5 text-gray-400 hover:text-gray-500"
                >
                  <span className="sr-only">Next month</span>
                  <ChevronRightIcon className="w-5 h-5" aria-hidden="true" />
                </button>
              </div>
              <div className="grid grid-cols-7 mt-10 text-xs leading-6 text-center text-gray-500">
                <div>M</div>
                <div>T</div>
                <div>W</div>
                <div>T</div>
                <div>F</div>
                <div>S</div>
                <div>S</div>
              </div>
              <div className="grid grid-cols-7 mt-2 text-sm">
                {days.map((day, dayIdx) => (
                  <div
                    key={day.toString()}
                    className={classNames(
                      dayIdx === 0 && colStartClasses[getDay(day - 1)],
                      "py-1.5"
                    )}
                  >
                    <button
                      type="button"
                      onClick={() => setSelectedDay(day)}
                      className={classNames(
                        isEqual(day, selectedDay) && "text-white",
                        !isEqual(day, selectedDay) &&
                          isToday(day) &&
                          "text-green-500",
                        !isEqual(day, selectedDay) &&
                          !isToday(day) &&
                          isSameMonth(day, firstDayCurrentMonth) &&
                          "text-gray-900",
                        !isEqual(day, selectedDay) &&
                          !isToday(day) &&
                          !isSameMonth(day, firstDayCurrentMonth) &&
                          "text-gray-400",
                        isEqual(day, selectedDay) &&
                          isToday(day) &&
                          "bg-green-500",
                        isEqual(day, selectedDay) &&
                          !isToday(day) &&
                          "bg-green-900",
                        !isEqual(day, selectedDay) && "hover:bg-gray-200",
                        (isEqual(day, selectedDay) || isToday(day)) &&
                          "font-semibold",
                        "mx-auto flex h-8 w-8 items-center justify-center rounded-full"
                      )}
                    >
                      <time dateTime={format(day, "yyyy-MM-dd")}>
                        {format(day, "d")}
                      </time>
                    </button>

                    <div className="w-1 h-1 mx-auto mt-1">
                      {meetings.some((meeting) =>
                        isSameDay(parseISO(meeting.startDate), day)
                      ) && (
                        <div className="w-1 h-1 rounded-full bg-yellow-400"></div>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            </div>
            <section className="mt-12 md:mt-0 md:pl-14 max-h-96">
              <h2 className="font-semibold text-gray-900">
                Schedule for{" "}
                <time dateTime={format(selectedDay, "yyyy-MM-dd")}>
                  {format(selectedDay, "MMM dd, yyy")}
                </time>
              </h2>
              <ol
                className={classNames(
                  selectedDayMeetings.length > 4
                    ? "hover:scrollbar-thin scrollbar-none overflow-y-scroll"
                    : "",
                  "max-h-72 mt-4 space-y-1 text-sm leading-6 text-gray-500"
                )}
              >
                {selectedDayMeetings.length > 0 ? (
                  selectedDayMeetings.map((meeting) => (
                    <Meeting meeting={meeting} key={meeting.id} />
                  ))
                ) : (
                  <p>No meetings for today.</p>
                )}
              </ol>
            </section>
          </div>
        </div>
        <div className="mt-4 max-w-md md:max-w-4xl mx-auto flex justify-between max-h-8">
          <div className="pl-4 h-8">
            <button
              onClick={toggleFilterModal}
              className="group h-8 shadow-md
               flex justify-between gap-1 py-1.5 
               text-xs rounded px-6 border-2 border-green-600
                text-green-600 font-medium leading-tight uppercase
                 hover:bg-green-600 hover:text-white
                  transition duration-150 ease-in-out"
            >
              <FilterIcon
                className="-ml-3 mr-3 w-4 h-4
               text-green-600 group-hover:block
                group-hover:text-white"
              />
              <span> Filter</span>
            </button>
          </div>
          {!isMobile ? (
            <div className="pr-4">
              <MeetingOwnershipFilter
                filter={ownershipFilter}
                toggle={meetingOwnershipFilterToggle}
              />
            </div>
          ) : (
            <MeetingOwnershipDropup
              filter={ownershipFilter}
              toggle={meetingOwnershipFilterToggle}
            />
          )}
        </div>
      </div>
    </>
  );
}

function Meeting({ meeting }) {
  let startDate = parseISO(meeting.startDate);
  let endDate = parseISO(meeting.endDate);

  return (
    <li className="flex items-center px-4 py-2 space-x-4 group rounded-xl focus-within:bg-gray-100 hover:bg-gray-100">
      <img
        src={meeting.responsiblePerson.profilePicture}
        alt="meeting logo"
        className="flex-none w-10 h-10 rounded-full object-cover"
      />
      <div className="flex-auto">
        <p className="text-gray-900">{meeting.name}</p>
        <p className="mt-0.5">
          <time dateTime={meeting.startDate}>{format(startDate, "HH:mm")}</time>{" "}
          - <time dateTime={meeting.endDate}>{format(endDate, "HH:mm")}</time>
        </p>
      </div>
      <Menu
        as="div"
        className="relative opacity-0 focus-within:opacity-100 group-hover:opacity-100"
      >
        <div>
          <Menu.Button className="-m-2 flex items-center rounded-full p-1.5 text-gray-500 hover:text-gray-600">
            <span className="sr-only">Open options</span>
            <DotsVerticalIcon className="w-6 h-6" aria-hidden="true" />
          </Menu.Button>
        </div>

        <Transition
          as={Fragment}
          enter="transition ease-out duration-100"
          enterFrom="transform opacity-0 scale-95"
          enterTo="transform opacity-100 scale-100"
          leave="transition ease-in duration-75"
          leaveFrom="transform opacity-100 scale-100"
          leaveTo="transform opacity-0 scale-95"
        >
          <Menu.Items className="absolute right-0 z-10 mt-2 origin-top-right bg-white rounded-md shadow-lg w-36 ring-1 ring-black ring-opacity-5 focus:outline-none">
            <div className="py-1">
              <Menu.Item>
                {({ active }) => (
                  <a
                    href="#"
                    className={classNames(
                      active ? "bg-gray-100 text-gray-900" : "text-gray-700",
                      "block px-4 py-2 text-sm"
                    )}
                  >
                    Edit
                  </a>
                )}
              </Menu.Item>
              <Menu.Item>
                {({ active }) => (
                  <a
                    href="#"
                    className={classNames(
                      active ? "bg-gray-100 text-gray-900" : "text-gray-700",
                      "block px-4 py-2 text-sm"
                    )}
                  >
                    Cancel
                  </a>
                )}
              </Menu.Item>
            </div>
          </Menu.Items>
        </Transition>
      </Menu>
    </li>
  );
}

let colStartClasses = [
  "",
  "col-start-2",
  "col-start-3",
  "col-start-4",
  "col-start-5",
  "col-start-6",
  "col-start-7",
];
