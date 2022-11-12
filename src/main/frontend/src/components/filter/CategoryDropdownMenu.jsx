import { Fragment } from "react";
import { Menu, Transition } from "@headlessui/react";
import { ChevronDownIcon } from "@heroicons/react/solid";
import classNames from "../../util/classNames";

export default function CategoryDropdownMenu(props) {
  const CATEGORIES = ["codeMonkey", "hub", "short", "teamBuilding"];

  return (
    <Menu
      as="div"
      className="relative w-full text-right 
      sm:w-56 h-10 mt-2 sm:mt-0 justify-self-end"
    >
      <Menu.Button
        className="w-full inline-flex justify-center 
         rounded-md border-2 border-gray-200 shadow-sm px-4 py-2
         bg-white text-sm font-medium text-gray-700 
         hover:bg-gray-50 hover:border-yellow-400 
         focus:outline-none h-10"
      >
        {!props.category ? (
          <>
            <p>Category</p>
            <ChevronDownIcon
              className="-mr-1 ml-2 h-5 w-5"
              aria-hidden="true"
            />
          </>
        ) : (
          <div className="hover:bg-gray-50 w-full">
            <p className="text-center w-full">
              {props.category.replace(/^\w/, (c) => c.toUpperCase())}
            </p>
          </div>
        )}
      </Menu.Button>

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
          className="overflow-y-auto h-40 
        origin-bottom-right absolute right-0 w-full rounded-md 
        shadow-lg bg-white ring-1 ring-black ring-opacity-5 divide-y
      divide-gray-100 focus:outline-none
        hover:scrollbar-thin scrollbar-none z-20"
        >
          <div className="py-1">
            {CATEGORIES.map((cat, index) => (
              <Menu.Item key={index}>
                {({ active }) => (
                  <div className=" hover:bg-gray-100 w-full">
                    <button
                      className={classNames(
                        active ? "text-gray-900" : "text-gray-700",
                        "px-4 py-2 text-sm w-full text-left pl-6"
                      )}
                      value={cat}
                      onClick={props.categoryFilterToggle}
                    >
                      {cat.replace(/^\w/, (c) => c.toUpperCase())}
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
