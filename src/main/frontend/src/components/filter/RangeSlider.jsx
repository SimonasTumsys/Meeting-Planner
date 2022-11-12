import { useState } from "react";
import Box from "@mui/material/Box";
import Slider from "@mui/material/Slider";
import classNames from "../../util/classNames";

function valuetext(value) {
  return `${value}`;
}

export default function RangeSlider({
  attendeeCount,
  handleCountFilterChange,
}) {
  const [popoverIsShowing, setPopoverIsShowing] = useState(false);

  return (
    <div on className="mt-1 flex align-middle items-center h-10 ml-1">
      <div className="w-full flex justify-center">
        <Box sx={{ width: 3 / 4 }}>
          <Slider
            onMouseEnter={() => setPopoverIsShowing(true)}
            onMouseLeave={() => setPopoverIsShowing(false)}
            size="small"
            min={0}
            max={40}
            className="mt-3"
            getAriaLabel={() => "Attendee count"}
            value={attendeeCount}
            onChange={handleCountFilterChange}
            valueLabelDisplay="auto"
            getAriaValueText={valuetext}
            sx={{
              color: "success.main",
            }}
          />
        </Box>
        <div
          className={classNames(
            popoverIsShowing ? "block" : "hidden",
            "absolute mx-auto mt-7 text-gray-400 font-extralight",
            "text-sm select-none pointer-events-none "
          )}
        >
          # of attendees
        </div>
      </div>
    </div>
  );
}
