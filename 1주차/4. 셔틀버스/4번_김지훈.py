def solution(n, t, m, timetable):
    timetable = sorted([int(h.split(':')[0]) * 60 + int(h.split(':')[1]) for h in timetable])
    shuttle_times = [9 * 60 + i * t for i in range(n)]
    for i in range(n - 1):
        shuttle = shuttle_times[i]
        boarded = 0
        while timetable and timetable[0] <= shuttle and boarded < m:
            timetable.pop(0)
            boarded += 1
    last_shuttle = shuttle_times[-1]
    waiting_crews = [time for time in timetable if time <= last_shuttle]
    if len(waiting_crews) < m:
        last_time = last_shuttle
    else:
        last_time = waiting_crews[m - 1] - 1
    hours = last_time // 60
    minutes = last_time % 60
    return f"{hours:02d}:{minutes:02d}"
