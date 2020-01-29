using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RadioAlarm {
    public interface IDateTimePickerPage {
        DateTime? Value { get; set; }
    }
}
