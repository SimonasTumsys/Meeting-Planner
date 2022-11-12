import { Fragment } from "react";
import { Menu, Transition } from "@headlessui/react";
import { ChevronUpIcon } from "@heroicons/react/solid";
import classNames from "../../util/classNames";

export default function MeetingOwnershipDropdown(props) {
  return (
    <Menu as="div" className="relative inline-block text-left w-40 h-10">
      <Transition
        as={Fragment}
        enter="transition ease-out duration-100"
        enterFrom="transform opacity-0 scale-95"
        enterTo="transform opacity-100 scale-100"
        leave="transition ease-in duration-75"
        leaveFrom="transform opacity-100 scale-100"
        leaveTo="transform opacity-0 scale-95"
      >
        <div className="bottom-0">
          <Menu.Items
            className="overflow-y-auto h-30 w-30
              origin-bottom-right absolute right-0 w-full rounded-md 
              shadow-lg bg-white ring-1 ring-black ring-opacity-5 divide-y
              divide-gray-100 focus:outline-none 
              hover:scrollbar-thin scrollbar-none bottom-1"
          >
            <div className="py-1">
              <Menu.Item>
                {({ active }) => (
                  <div className=" hover:bg-gray-100 w-full">
                    <button
                      className={classNames(
                        active ? "text-gray-900" : "text-gray-700",
                        "px-4 py-2 text-sm w-full text-left "
                      )}
                      value="all"
                      onClick={props.toggle}
                    >
                      ALL
                    </button>
                  </div>
                )}
              </Menu.Item>
              <Menu.Item>
                {({ active }) => (
                  <div className=" hover:bg-gray-100 w-full">
                    <button
                      className={classNames(
                        active ? "text-gray-900" : "text-gray-700",
                        "px-4 py-2 text-sm w-full text-left "
                      )}
                      value="responsible"
                      onClick={props.toggle}
                    >
                      RESPONSIBLE
                    </button>
                  </div>
                )}
              </Menu.Item>
              <Menu.Item>
                {({ active }) => (
                  <div className=" hover:bg-gray-100 w-full">
                    <button
                      className={classNames(
                        active ? "text-gray-900" : "text-gray-700",
                        "px-4 py-2 text-sm w-full text-left "
                      )}
                      value="attending"
                      onClick={props.toggle}
                    >
                      ATTENDING
                    </button>
                  </div>
                )}
              </Menu.Item>
              <Menu.Item>
                {({ active }) => (
                  <div className=" hover:bg-gray-100 w-full">
                    <button
                      className={classNames(
                        active ? "text-gray-900" : "text-gray-700",
                        "px-4 py-2 text-sm w-full text-left "
                      )}
                      value="myMeetings"
                      onClick={props.toggle}
                    >
                      MY MEETINGS
                    </button>
                  </div>
                )}
              </Menu.Item>
            </div>
          </Menu.Items>
        </div>
      </Transition>

      <div className="h-8 w-30 mr-3 ml-6">
        <Menu.Button className="flex justify-center  h-8 rounded w-full bg-green-800 text-white font-medium text-xs leading-tight uppercase hover:bg-green-600  focus:outline-none focus:ring-0 transition duration-150 ease-in-out">
          <div className="my-auto flex">
            <p>
              {props.filter === "myMeetings" ? "My meetings" : props.filter}
            </p>
            <ChevronUpIcon
              className="-mr-1 ml-2 h-5 w-5 pb-0.5"
              aria-hidden="true"
            />
          </div>
        </Menu.Button>
      </div>
    </Menu>
  );
}
