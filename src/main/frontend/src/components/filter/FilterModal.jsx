import { Fragment, useRef, useState, useEffect } from "react";
import { Dialog, Transition } from "@headlessui/react";
import { FilterIcon } from "@heroicons/react/solid";
import RespDropdownMenu from "./RespDropdownMenu";
import CategoryDropdownMenu from "./CategoryDropdownMenu";
import TypeDropdownMenu from "./TypeDropdownMenu";
import RangeSlider from "./RangeSlider";
import useMeetingFilter from "../../hooks/useMeetingFilter";
import useTempState from "../../hooks/useTempState";

const FilterModal = (props) => {
  const toggleFilterModal = props.toggleFilterModal;

  const onFilterButtonClick = () => {
    toggleFilterModal();
  };

  return (
    <Transition.Root show={props.filterModal} as={Fragment}>
      <Dialog
        as="div"
        className="fixed z-10 inset-0 overflow-y-auto"
        onClose={props.toggleFilterModal}
      >
        <div
          className="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block
             sm:p-0"
        >
          <Transition.Child
            as={Fragment}
            enter="ease-out duration-300"
            enterFrom="opacity-0"
            enterTo="opacity-100"
            leave="ease-in duration-200"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <Dialog.Overlay
              className="fixed inset-0 bg-gray-500 
            bg-opacity-75 transition-opacity"
            />
          </Transition.Child>

          <span
            className="hidden sm:inline-block sm:align-middle sm:h-screen"
            aria-hidden="true"
          >
            &#8203;
          </span>
          <Transition.Child
            as={Fragment}
            enter="ease-out duration-300"
            enterFrom="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
            enterTo="opacity-100 translate-y-0 sm:scale-100"
            leave="ease-in duration-200"
            leaveFrom="opacity-100 translate-y-0 sm:scale-100"
            leaveTo="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
          >
            <div
              className="inline-block align-bottom bg-white rounded-lg
                   text-left 
                 shadow-xl 
                transform transition-all 
                sm:my-8 sm:align-middle sm:max-w-lg sm:w-full"
            >
              <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <div className="flex justify-start gap-3 sm:items-start sm:justify-start">
                  <div
                    className="ml-4 flex-shrink-0 flex items-center
                       justify-center h-12 w-12 rounded-full bg-green-200 sm:mx-0
                        sm:h-10 sm:w-10"
                  >
                    <FilterIcon
                      className="h-6 w-6 text-white"
                      aria-hidden="true"
                    />
                  </div>
                  <div
                    className="mt-2 text-center 
                  sm:mt-0 sm:ml-2 sm:text-left"
                  >
                    <Dialog.Title
                      as="h3"
                      className="text-lg
                       leading-6 font-medium text-gray-900 mt-2 pl-2"
                    >
                      Filter by
                    </Dialog.Title>
                  </div>
                </div>
                <div className="mt-4">
                  <div className="w-full">
                    <input
                      className="filter-input-field"
                      type="text"
                      placeholder="Description"
                      value={props.description}
                      onChange={props.handleDescriptionChange}
                    />
                  </div>
                  <div className="mt-2 sm:grid sm:grid-cols-2">
                    <RespDropdownMenu
                      responsiblePeople={props.responsiblePeople}
                      responsiblePersonFilterToggle={
                        props.responsiblePersonFilterToggle
                      }
                      responsiblePerson={props.responsiblePerson}
                    />
                    <CategoryDropdownMenu
                      category={props.category}
                      categoryFilterToggle={props.categoryFilterToggle}
                    />
                    <TypeDropdownMenu
                      type={props.type}
                      typeFilterToggle={props.typeFilterToggle}
                    />
                    <RangeSlider
                      attendeeCount={props.attendeeCount}
                      handleCountFilterChange={props.handleCountFilterChange}
                    />
                  </div>
                </div>
              </div>
              <div
                className="bg-gray-50 px-4 py-3 
              sm:px-6 sm:flex sm:justify-between sm:flex-row-reverse"
              >
                <div className="sm:flex sm:flex-row-reverse">
                  <button
                    type="button"
                    className="w-full inline-flex justify-center rounded-md
                       border border-transparent shadow-sm px-4 py-2 bg-green-600
                        text-base font-medium text-white hover:bg-green-700 
                        focus:outline-none hover:ring-2 hover:ring-offset-1
                         hover:ring-yellow-400 sm:ml-3 sm:w-auto sm:text-sm"
                    onClick={onFilterButtonClick}
                  >
                    Filter
                  </button>
                  <button
                    type="button"
                    className="mt-3 w-full inline-flex justify-center
                      rounded-md border border-gray-300 shadow-sm px-4 py-2
                       bg-white text-base font-medium text-gray-700
                        hover:bg-gray-50 focus:outline-none hover:ring-2
                         focus:ring-offset-1 hover:ring-yellow-400 sm:mt-0
                          sm:ml-3 sm:w-auto sm:text-sm"
                    onClick={props.toggleFilterModal}
                  >
                    Cancel
                  </button>
                </div>
                {props.description !== "" ? (
                  <button
                    type="button"
                    className="mt-3 w-full inline-flex justify-center
                      rounded-md border border-gray-300 shadow-sm px-4 py-2
                       bg-white text-base font-medium text-gray-700
                        hover:bg-gray-50 focus:outline-none hover:ring-2
                         focus:ring-offset-1 hover:ring-yellow-400 sm:mt-0
                        sm:w-auto sm:text-sm"
                    onClick={props.resetMeetingFilter}
                  >
                    Reset
                  </button>
                ) : (
                  <></>
                )}
              </div>
            </div>
          </Transition.Child>
        </div>
      </Dialog>
    </Transition.Root>
  );
};

export default FilterModal;
