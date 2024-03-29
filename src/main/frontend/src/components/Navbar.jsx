import React, { useState, setState } from "react";
import { useMediaQuery } from "react-responsive";
import { MenuIcon, XIcon } from "@heroicons/react/outline";
import logoImg from "../assets/meeting_planner_logo.png";

const Navbar = (props) => {
  const isMobile = useMediaQuery({ query: `(max-width: 760px)` });
  const [nav, setNav] = useState(false);
  const handleNavClick = () => setNav(!nav);

  return (
    <div className="w-screen h-[80px] z-10 bg-zinc-200 fixed drop-shadow-lg">
      <div className="px-2 flex justify-between items-center w-full h-full">
        <div>
          <img
            className="w-32 mt-4 ml-4"
            src={logoImg}
            alt="couldn't load img"
          />
        </div>
        <div className="flex items-center justify-between">
          <ul className="hidden md:flex items-center">
            <li>Home</li>
            <li>Calendar</li>
            <li>Your meetings</li>
            <li>Create meeting</li>
            <li>About</li>
          </ul>
        </div>
        <div className="hidden md:flex pr-4">
          <button className="px-8 py-3" onClick={props.updateAuth}>
            Logout
          </button>
        </div>
        <div className="md:hidden" onClick={handleNavClick}>
          {!nav ? (
            <MenuIcon className="w-6 mr-6" />
          ) : (
            <XIcon className="w-6 mr-6" />
          )}
        </div>
      </div>

      <ul
        className={
          !nav
            ? "hidden"
            : isMobile
            ? "absolute bg-zinc-200 w-full px-8"
            : "hidden"
        }
      >
        <li className="border-b-2 border-zinc-300 w-full">Home</li>
        <li className="border-b-2 border-zinc-300 w-full">Calendar</li>
        <li className="border-b-2 border-zinc-300 w-full">Your meetings</li>
        <li className="border-b-2 border-zinc-300 w-full">Create meeting</li>
        <li className="border-b-2 border-zinc-300 w-full">About</li>
        <div className="flex flex-col my-4">
          <button className="px-8 py-3" onClick={props.updateAuth}>
            Logout
          </button>
        </div>
      </ul>
    </div>
  );
};

export default Navbar;
