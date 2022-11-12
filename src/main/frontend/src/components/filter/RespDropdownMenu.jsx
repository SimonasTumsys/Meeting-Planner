import { Fragment } from "react";
import { Menu, Transition } from "@headlessui/react";
import { ChevronDownIcon } from "@heroicons/react/solid";
import classNames from "../../util/classNames";

export default function RespDropdownMenu(props) {
  const responsiblePeople = props.responsiblePeople;

  return (
    <Menu
      as="div"
      className="relative w-full inline-block text-left sm:w-56 h-10"
    >
      <div className="h-10">
        <Menu.Button
          className="w-full inline-flex justify-center 
         rounded-md border-2 border-gray-200 shadow-sm px-4 py-2
         bg-white text-sm font-medium text-gray-700 
         hover:bg-gray-50 hover:border-yellow-400 
         focus:outline-none h-10"
        >
          {!props.responsiblePerson ? (
            <>
              <p>Responsible Person</p>
              <ChevronDownIcon
                className="-mr-1 ml-2 h-5 w-5"
                aria-hidden="true"
              />
            </>
          ) : (
            <div className="grid grid-cols-10 hover:bg-gray-50 w-full">
              <img
                className="w-6 h-6 rounded-full 
                      object-cover col-span-2 self-center"
                src={props.responsiblePerson.profilePicture}
                alt={props.responsiblePerson.name + " image"}
              />

              <p className="col-span-8 text-left ml-2 w-full px-4">
                {props.responsiblePerson.name +
                  " " +
                  props.responsiblePerson.surname}
              </p>
            </div>
          )}
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
        <Menu.Items
          className="overflow-y-auto h-48 
        origin-top-right absolute right-0 w-full rounded-md 
        shadow-lg bg-white ring-1 ring-black ring-opacity-5 divide-y
         divide-gray-100 focus:outline-none hover:scrollbar-thin scrollbar-none z-20"
        >
          <div className="py-1">
            {responsiblePeople.map((respPerson) => (
              <Menu.Item key={respPerson.id}>
                {({ active }) => (
                  <div className="grid grid-cols-10 hover:bg-gray-100 w-full">
                    <img
                      className="mr-2 w-6 h-6 rounded-full 
                      object-cover ml-4 col-span-2 self-center"
                      src={respPerson.profilePicture}
                      alt={respPerson.name + " image"}
                    />
                    <button
                      className={classNames(
                        active ? "text-gray-900" : "text-gray-700",
                        "px-8 py-2 text-sm w-full text-left col-span-8"
                      )}
                      value={respPerson.id}
                      onClick={props.responsiblePersonFilterToggle}
                    >
                      {respPerson.name + " " + respPerson.surname}
                    </button>
                  </div>
                )}
              </Menu.Item>
            ))}
          </div>
        </Menu.Items>
      </Transition>
    </Menu>
  );
}
